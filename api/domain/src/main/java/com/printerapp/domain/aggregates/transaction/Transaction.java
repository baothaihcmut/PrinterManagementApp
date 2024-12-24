package com.printerapp.domain.aggregates.transaction;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.document.value_objects.ObjectLink;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.transaction.entities.TransactionDocument;
import com.printerapp.domain.aggregates.transaction.value_objecs.DocumentDetail;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.exceptions.transactions.ExceedDocumentLimitException;
import com.printerapp.domain.exceptions.transactions.NotEmployeeException;
import com.printerapp.domain.exceptions.transactions.NotEmployeeTransactionException;
import com.printerapp.domain.exceptions.transactions.UnvalidTransactionStatus;
import com.printerapp.domain.models.BaseAggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseAggregate<TransactionId> {
    private String name;
    private String transactionId;
    private UserId customerId;
    private UserId employeeId;
    private PrinterId printerId;
    private PrintTransactionStatus status;
    private List<TransactionDocument> transactionDocuments = new ArrayList<>();
    private Map<PaperType, PaperQuantity> transactionPaperQuantities = new HashMap<>();
    private LocalDateTime doneAt;
    private LocalDateTime acceptedAt;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static String generateRandomTransactionId(int length) {
        if (length < 1)
            throw new IllegalArgumentException("Length must be greater than 0");

        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    public Transaction(UserId customerId, PrinterId printerId, String name, UserName customerName) {
        super(new TransactionId(UUID.randomUUID()));
        this.transactionId = Transaction.generateRandomTransactionId(8);
        this.name = name;
        this.status = PrintTransactionStatus.PENDING;
        this.customerId = customerId;
        this.printerId = printerId;
        // init paper quantities
        PaperQuantity paperQuantityA3 = new PaperQuantity(PaperType.A3, 0);
        PaperQuantity paperQuantityA4 = new PaperQuantity(PaperType.A4, 0);
        PaperQuantity paperQuantityA5 = new PaperQuantity(PaperType.A5, 0);
        this.transactionPaperQuantities.put(PaperType.A3, paperQuantityA3);
        this.transactionPaperQuantities.put(PaperType.A4, paperQuantityA4);
        this.transactionPaperQuantities.put(PaperType.A5, paperQuantityA5);
    }

    public void addDocument(DocumentId documentId, String name, DocumentDetail detail, ObjectLink link)
            throws ExceedDocumentLimitException {
        if (this.transactionDocuments.size() == 5) {
            throw new ExceedDocumentLimitException(5);
        }
        TransactionDocument newTransaction = new TransactionDocument(documentId, name, detail, link);
        PaperQuantity totalPaper = newTransaction.getTotalPaper();
        PaperQuantity current = this.transactionPaperQuantities.get(totalPaper.getType());
        this.transactionPaperQuantities.put(totalPaper.getType(), current.add(totalPaper));
        this.transactionDocuments.add(newTransaction);
    }

    public void removeDocument(TransactionDocument document) {
        boolean isDeleted = this.transactionDocuments.remove(document);
        if (!isDeleted) {
            throw new IllegalArgumentException("Document not exist");
        }
    }

    public void acceptTransaction(User user) throws UnvalidTransactionStatus {
        if (!this.status.equals(PrintTransactionStatus.PENDING)) {
            throw new UnvalidTransactionStatus("Transaction can't be accepted");
        }
        if (!user.getRole().equals(Role.EMPLOYEE)) {
            throw new NotEmployeeException();
        }
        this.employeeId = user.getId();
        this.status = PrintTransactionStatus.PROCESS;
        this.acceptedAt = LocalDateTime.now();
    }

    public void doneTransaction(User user)
            throws UnvalidTransactionStatus, NotEmployeeException, NotEmployeeTransactionException {
        if (!this.status.equals(PrintTransactionStatus.PROCESS)) {
            throw new UnvalidTransactionStatus("Transaction is not processing");
        }
        if (!user.getRole().equals(Role.EMPLOYEE)) {
            throw new NotEmployeeException();
        }
        System.out.println(user.getId());
        System.err.println(this.getEmployeeId());
        if (!user.getId().equals(this.getEmployeeId())) {
            throw new NotEmployeeTransactionException();
        }
        this.doneAt = LocalDateTime.now();
        this.status = PrintTransactionStatus.DONE;
    }

    public void failureTransaction(User user)
            throws UnvalidTransactionStatus, NotEmployeeException, NotEmployeeTransactionException {
        if (!this.status.equals(PrintTransactionStatus.PENDING)) {
            throw new UnvalidTransactionStatus("Transaction is not processing");
        }
        if (!user.getRole().equals(Role.EMPLOYEE)) {
            throw new NotEmployeeException();
        }
        if (!user.getId().equals(this.getEmployeeId())) {
            throw new NotEmployeeTransactionException();
        }
        this.status = PrintTransactionStatus.FAILURE;
    }

}
