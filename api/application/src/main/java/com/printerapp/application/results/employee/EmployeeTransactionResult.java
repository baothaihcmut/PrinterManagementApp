package com.printerapp.application.results.employee;

import java.time.LocalDateTime;

import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeTransactionResult {
    private TransactionId id;
    private String transactionId;
    private String name;
    private UserId customerId;
    private UserName customerName;
    private LocalDateTime createdAt;
    private PrintTransactionStatus status;
}
