package com.printerapp.application.results.printers;

import java.time.LocalDateTime;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrinterTransactionResult {
    private TransactionId id;
    private String transactionId;
    private UserId customerId;
    private UserName customerName;
    private UserId employeeId;
    private UserName employeeName;
    private PrinterId printerId;
    private String name;
    private PrintTransactionStatus status;
    private LocalDateTime createdAt;
}
