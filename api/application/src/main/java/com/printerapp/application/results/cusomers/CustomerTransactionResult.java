package com.printerapp.application.results.cusomers;

import java.time.LocalDateTime;

import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerTransactionResult {
    private TransactionId id;
    private String transactionId;
    private String name;
    private UserId employeeId;
    private UserName employeeName;

    private LocalDateTime createdAt;
    private PrintTransactionStatus status;
}
