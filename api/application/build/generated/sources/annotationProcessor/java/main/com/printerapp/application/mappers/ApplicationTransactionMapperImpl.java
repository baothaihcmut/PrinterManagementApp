package com.printerapp.application.mappers;

import com.printerapp.application.commands.transactions.CreateTransactionCommand;
import com.printerapp.application.results.transactions.AdminTransactionResult;
import com.printerapp.application.results.transactions.TransactionPrinterResult;
import com.printerapp.application.results.transactions.TransactionResult;
import com.printerapp.application.results.transactions.TransactionUserResult;
import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.entities.TransactionDocument;
import com.printerapp.domain.aggregates.transaction.value_objecs.DocumentDetail;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.domain.enums.Role;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-23T13:33:09+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 23 (Homebrew)"
)
@Component
public class ApplicationTransactionMapperImpl implements ApplicationTransactionMapper {

    @Override
    public TransactionResult toResult(Transaction transaction, List<String> urls) {
        if ( transaction == null && urls == null ) {
            return null;
        }

        TransactionId id = null;
        String transactionId = null;
        UserId customerId = null;
        UserId employeeId = null;
        PrinterId printerId = null;
        String name = null;
        PrintTransactionStatus status = null;
        Map<PaperType, PaperQuantity> transactionPaperQuantities = null;
        List<TransactionDocument> transactionDocuments = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        LocalDateTime acceptedAt = null;
        LocalDateTime doneAt = null;
        if ( transaction != null ) {
            id = transaction.getId();
            transactionId = transaction.getTransactionId();
            customerId = transaction.getCustomerId();
            employeeId = transaction.getEmployeeId();
            printerId = transaction.getPrinterId();
            name = transaction.getName();
            status = transaction.getStatus();
            Map<PaperType, PaperQuantity> map = transaction.getTransactionPaperQuantities();
            if ( map != null ) {
                transactionPaperQuantities = new LinkedHashMap<PaperType, PaperQuantity>( map );
            }
            List<TransactionDocument> list = transaction.getTransactionDocuments();
            if ( list != null ) {
                transactionDocuments = new ArrayList<TransactionDocument>( list );
            }
            createdAt = transaction.getCreatedAt();
            updatedAt = transaction.getUpdatedAt();
            acceptedAt = transaction.getAcceptedAt();
            doneAt = transaction.getDoneAt();
        }

        List<String> urls1 = mapUrls(urls);
        UserName customerName = null;
        UserId employeeName = null;

        TransactionResult transactionResult = new TransactionResult( id, transactionId, customerId, customerName, employeeId, employeeName, printerId, name, status, transactionPaperQuantities, transactionDocuments, createdAt, updatedAt, acceptedAt, doneAt, urls1 );

        return transactionResult;
    }

    @Override
    public AdminTransactionResult toAdminTransactionResult(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionId id = null;
        String transactionId = null;
        String name = null;
        UserId customerId = null;
        UserId employeeId = null;
        LocalDateTime createdAt = null;
        PrintTransactionStatus status = null;

        id = transaction.getId();
        transactionId = transaction.getTransactionId();
        name = transaction.getName();
        customerId = transaction.getCustomerId();
        employeeId = transaction.getEmployeeId();
        createdAt = transaction.getCreatedAt();
        status = transaction.getStatus();

        UserName customerName = null;
        UserName employeeName = null;

        AdminTransactionResult adminTransactionResult = new AdminTransactionResult( id, transactionId, name, customerId, customerName, employeeId, employeeName, createdAt, status );

        return adminTransactionResult;
    }

    @Override
    public DocumentDetail toDocumentDetailDomain(CreateTransactionCommand.TransactionDocumentDetail detail) {
        if ( detail == null ) {
            return null;
        }

        Integer leftSide = null;
        Integer rightSide = null;
        Integer topSide = null;
        Integer bottomSide = null;
        Integer numOfPageOneSide = null;
        Boolean isLandscape = null;
        PaperType paperType = null;
        Integer numOfCopies = null;
        Integer fromPage = null;
        Integer toPage = null;
        Boolean isOneSide = null;

        if ( detail.getLeftSide() != null ) {
            leftSide = detail.getLeftSide();
        }
        else {
            leftSide = 10;
        }
        if ( detail.getRightSide() != null ) {
            rightSide = detail.getRightSide();
        }
        else {
            rightSide = 10;
        }
        if ( detail.getTopSide() != null ) {
            topSide = detail.getTopSide();
        }
        else {
            topSide = 10;
        }
        if ( detail.getBottomSide() != null ) {
            bottomSide = detail.getBottomSide();
        }
        else {
            bottomSide = 10;
        }
        if ( detail.getNumOfPageOneSide() != null ) {
            numOfPageOneSide = detail.getNumOfPageOneSide();
        }
        else {
            numOfPageOneSide = 1;
        }
        isLandscape = detail.getIsLandscape();
        paperType = detail.getPaperType();
        numOfCopies = detail.getNumOfCopies();
        fromPage = detail.getFromPage();
        toPage = detail.getToPage();
        isOneSide = detail.getIsOneSide();

        DocumentDetail documentDetail = new DocumentDetail( paperType, numOfCopies, isLandscape, fromPage, toPage, leftSide, rightSide, topSide, bottomSide, isOneSide, numOfPageOneSide );

        return documentDetail;
    }

    @Override
    public TransactionUserResult toUserResult(User user) {
        if ( user == null ) {
            return null;
        }

        UserId id = null;
        UserName name = null;
        Email email = null;
        Role role = null;
        String phoneNumber = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        role = user.getRole();
        phoneNumber = user.getPhoneNumber();

        TransactionUserResult transactionUserResult = new TransactionUserResult( id, name, email, role, phoneNumber );

        return transactionUserResult;
    }

    @Override
    public TransactionPrinterResult toPrinterResult(Printer printer) {
        if ( printer == null ) {
            return null;
        }

        PrinterId id = null;
        String name = null;
        String location = null;
        String code = null;
        PrinterStatus status = null;

        id = printer.getId();
        name = printer.getName();
        location = printer.getLocation();
        code = printer.getCode();
        status = printer.getStatus();

        TransactionPrinterResult transactionPrinterResult = new TransactionPrinterResult( id, name, location, code, status );

        return transactionPrinterResult;
    }
}
