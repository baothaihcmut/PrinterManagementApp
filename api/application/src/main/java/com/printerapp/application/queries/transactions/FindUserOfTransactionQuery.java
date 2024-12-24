package com.printerapp.application.queries.transactions;

import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindUserOfTransactionQuery {
    private TransactionId transactionId;
    private Role role;
}
