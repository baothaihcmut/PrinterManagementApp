package com.printerapp.application.commands.transactions;

import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTransactionStatusCommand {
    PrintTransactionStatus status;
    TransactionId transactionId;
}
