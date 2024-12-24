package com.printerapp.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.printerapp.application.commands.transactions.CreateTransactionCommand;
import com.printerapp.application.commands.transactions.UpdateTransactionStatusCommand;
import com.printerapp.application.exceptions.ConflictException;
import com.printerapp.application.exceptions.ForbiddenException;
import com.printerapp.application.exceptions.NotFoundException;
import com.printerapp.application.exceptions.StorageException;
import com.printerapp.application.mappers.ApplicationTransactionMapper;
import com.printerapp.application.queries.storage.GetPresignUrlGetQuery;
import com.printerapp.application.queries.storage.GetPresignUrlPutQuery;
import com.printerapp.application.queries.transactions.FindPrinterOfTransactionQuery;
import com.printerapp.application.queries.transactions.FindTransactionByIdQuery;
import com.printerapp.application.queries.transactions.FindTransactionQuery;
import com.printerapp.application.queries.transactions.FindUserOfTransactionQuery;
import com.printerapp.application.queries.transactions.SearchTransactionQuery;
import com.printerapp.application.results.auth.UserContext;
import com.printerapp.application.results.storage.GetPresignUrlGetResult;
import com.printerapp.application.results.storage.GetPresignUrlPutResult;
import com.printerapp.application.results.transactions.AdminTransactionResult;
import com.printerapp.application.results.transactions.TransactionPrinterResult;
import com.printerapp.application.results.transactions.TransactionResult;
import com.printerapp.application.results.transactions.TransactionUserResult;
import com.printerapp.domain.aggregates.document.Document;
import com.printerapp.domain.aggregates.document.value_objects.ObjectLink;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.events.AcceptTransactionEvent;
import com.printerapp.domain.aggregates.transaction.events.DoneTransactionEvent;
import com.printerapp.domain.aggregates.transaction.value_objecs.DocumentDetail;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.filter.Operator;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.exceptions.transactions.ExceedDocumentLimitException;
import com.printerapp.domain.exceptions.transactions.NotEmployeeException;
import com.printerapp.domain.exceptions.transactions.NotEmployeeTransactionException;
import com.printerapp.domain.exceptions.transactions.UnvalidTransactionStatus;
import com.printerapp.domain.records.transactions.TransactionRecord;
import com.printerapp.domain.repositories.DocumentRepository;
import com.printerapp.domain.repositories.PrinterEmployeeRepository;
import com.printerapp.domain.repositories.PrinterRepository;
import com.printerapp.domain.repositories.TransactionRepositoy;
import com.printerapp.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepositoy transactionRepositoy;
    private final PrinterEmployeeRepository printerEmployeeRepository;
    private final DocumentRepository documentRepository;
    private final AuthService authService;
    private final ApplicationTransactionMapper transactionMapper;
    private final StorageService storageService;
    private final UserRepository userRepository;
    private final PrinterRepository printerRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public TransactionResult createTransaction(CreateTransactionCommand transactionCommand) throws Exception {
        try {
            // get customer
            UserContext userContext = this.authService.getUserContext();
            System.out.println(userContext.getId());
            User user = this.userRepository
                    .findById(new UserId(userContext.getId()))
                    .orElseThrow(() -> new NotFoundException("User not found"));
            // check if printer exist
            this.printerRepository.findById(transactionCommand.getPrinterId())
                    .orElseThrow(() -> new NotFoundException("Printer not found"));

            Transaction transaction = new Transaction(new UserId(userContext.getId()),
                    transactionCommand.getPrinterId(),
                    transactionCommand.getName(), user.getName());
            // check customer account
            User customer = this.userRepository.findById(user.getId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            transaction.getTransactionPaperQuantities().values().stream().forEach((paperQuantity) -> {
                customer.getCustomer().subtractPaper(paperQuantity);
            });
            List<String> urls = new ArrayList<>();
            // process new document
            transactionCommand.getNewDocuments().forEach((document) -> {
                // get presign link for put
                GetPresignUrlPutResult url;
                try {
                    url = this.storageService
                            .getPresignUrlPut(GetPresignUrlPutQuery.builder().fileName(document.getName()).build());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new StorageException("Storage system error");
                }
                ObjectLink objectLink = new ObjectLink(url.getLink());
                DocumentDetail documentDetail = this.transactionMapper.toDocumentDetailDomain(document.getDetail());
                // create document
                Document newDocument = new Document(document.getName(), objectLink, document.getSave(),
                        new UserId(userContext.getId()));
                this.documentRepository.save(newDocument);
                // add document to transaction
                transaction.addDocument(newDocument.getId(), newDocument.getName(), documentDetail, objectLink);
                // app end to urls for response to FE
                urls.add(url.getUrl());
            });
            // process old document
            transactionCommand.getOldDocuments().forEach((document) -> {
                DocumentDetail documentDetail = this.transactionMapper.toDocumentDetailDomain(document.getDetail());
                Document oldDocument = this.documentRepository.findById(document.getId())
                        .orElseThrow(() -> new NotFoundException("Document not exist"));
                transaction.addDocument(oldDocument.getId(), oldDocument.getName(), documentDetail,
                        oldDocument.getLink());
            });
            // persist customer
            this.userRepository.save(customer);

            // persist transaction
            this.transactionRepositoy.save(transaction);
            return this.transactionMapper.toResult(transaction, urls);
        } catch (ExceedDocumentLimitException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public TransactionResult findById(FindTransactionByIdQuery query) throws Exception {
        // get transaction from db
        Transaction transaction = this.transactionRepositoy.findById(query.getTransactionId())
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
        List<String> urls = new ArrayList<>();
        transaction.getTransactionDocuments().forEach((document) -> {
            try {
                GetPresignUrlGetResult url = this.storageService
                        .getPresignUrlGet(
                                GetPresignUrlGetQuery.builder().objectName(document.getLink().getValue()).build());
                urls.add(url.getUrl());
            } catch (Exception e) {
                e.printStackTrace();
                throw new StorageException("Storage system exception");
            }
        });
        return this.transactionMapper.toResult(transaction, urls);
    }

    public TransactionUserResult findUserOfTransaction(FindUserOfTransactionQuery query) {
        Transaction transaction = this.transactionRepositoy.findById(query.getTransactionId())
                .orElseThrow(() -> new NotFoundException("Transaction not exist"));
        if (query.getRole().equals(Role.CUSTOMER)) {
            User customer = this.userRepository.findById(transaction.getCustomerId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            return this.transactionMapper.toUserResult(customer);
        } else {
            if (transaction.getStatus().equals(PrintTransactionStatus.PENDING) || transaction.getEmployeeId() == null) {
                throw new NotFoundException("Transaction is pending");
            }
            User employee = this.userRepository.findById(transaction.getEmployeeId())
                    .orElseThrow(() -> new NotFoundException("Employee not found"));
            return this.transactionMapper.toUserResult(employee);
        }
    }

    public TransactionPrinterResult findPrinterOfTransaction(FindPrinterOfTransactionQuery query) {
        Transaction transaction = this.transactionRepositoy.findById(query.getTransactionId())
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
        Printer printer = this.printerRepository.findById(transaction.getPrinterId())
                .orElseThrow(() -> new NotFoundException("Printer not found"));
        return this.transactionMapper.toPrinterResult(printer);
    }

    @Transactional
    public TransactionResult updateTransactionStatus(UpdateTransactionStatusCommand command) {
        try {
            // get employee
            UserContext userContext = this.authService.getUserContext();
            User employee = this.userRepository.findById(new UserId(userContext.getId()))
                    .orElseThrow(() -> new NotFoundException("User not exist"));
            Transaction transaction = this.transactionRepositoy.findById(command.getTransactionId())
                    .orElseThrow(() -> new NotFoundException("Transaction not found"));
            // check if employee of printer
            this.printerEmployeeRepository.findById(new PrinterEmployeeId(employee.getId(), transaction.getPrinterId()))
                    .orElseThrow(() -> new ForbiddenException("You aren't employee of this printer"));
            switch (command.getStatus()) {
                case PROCESS:
                    transaction.acceptTransaction(employee);
                    this.publisher.publishEvent(AcceptTransactionEvent.builder().employeeId(employee.getId())
                            .printerId(transaction.getPrinterId()).build());
                    break;
                case DONE:
                    transaction.doneTransaction(employee);
                    this.publisher.publishEvent(DoneTransactionEvent.builder().printerId(transaction.getPrinterId())
                            .employeeId(employee.getId()).build());
                    break;
                case FAILURE:
                    transaction.failureTransaction(employee);
                    break;
                default:
                    throw new ForbiddenException("Operation not allow");
            }
            this.transactionRepositoy.save(transaction);

            return this.transactionMapper.toResult(transaction, null);
        } catch (UnvalidTransactionStatus e) {
            throw new ConflictException(e.getMessage());
        } catch (NotEmployeeException | NotEmployeeTransactionException e) {
            throw new ForbiddenException(e.getMessage());
        }
    }

    public PaginatedResult<TransactionRecord> findTransaction(FindTransactionQuery query) {
        List<FilterParam<?>> filters = new ArrayList<>();
        if (query.getStatus() != null) {
            filters.add(
                    FilterParam.builder().field("status").value(query.getStatus())
                            .operator(Operator.EQUAL).build());
        }
        if (query.getUserId() != null) {
            if (query.getRole().equals(Role.CUSTOMER)) {
                filters.add(
                        FilterParam.builder().field("customer.id").value(query.getUserId().getValue())
                                .operator(Operator.EQUAL).build());
            } else {
                filters.add(FilterParam.builder().field("employee.id").value(query.getUserId().getValue())
                        .operator(Operator.EQUAL).build());
            }
        }
        return this.transactionRepositoy.findByCriteria(filters,
                query.getSortParam() == null ? new ArrayList<>() : List.of(query.getSortParam()),
                query.getPaginatedParam());

    }

    public PaginatedResult<AdminTransactionResult> searchTransaction(SearchTransactionQuery search) {
        return null;

    }
}
