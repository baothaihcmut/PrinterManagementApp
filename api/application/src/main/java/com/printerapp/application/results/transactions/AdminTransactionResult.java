package com.printerapp.application.results.transactions;

import java.time.LocalDateTime;

import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminTransactionResult {
    private TransactionId id;
    private String transactionId;
    private String name;
    private UserId customerId;
    private UserName customerName;
    private UserId employeeId;
    private UserName employeeName;
    private LocalDateTime createdAt;
    private PrintTransactionStatus status;
}
