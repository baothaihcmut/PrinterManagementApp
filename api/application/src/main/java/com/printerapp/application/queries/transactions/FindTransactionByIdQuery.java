package com.printerapp.application.queries.transactions;

import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindTransactionByIdQuery {
    private TransactionId transactionId;
}
