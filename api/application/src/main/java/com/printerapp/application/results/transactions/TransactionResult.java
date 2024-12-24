package com.printerapp.application.results.transactions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.transaction.entities.TransactionDocument;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResult {
    private TransactionId id;
    private String transactionId;
    private UserId customerId;
    private UserName customerName;
    private UserId employeeId;
    private UserId employeeName;
    private PrinterId printerId;
    private String name;
    private PrintTransactionStatus status;
    private Map<PaperType, PaperQuantity> transactionPaperQuantities;
    private List<TransactionDocument> transactionDocuments;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime acceptedAt;
    LocalDateTime doneAt;
    private List<String> urls;
}
