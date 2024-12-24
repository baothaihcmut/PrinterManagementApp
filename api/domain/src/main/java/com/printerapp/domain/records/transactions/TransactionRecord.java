package com.printerapp.domain.records.transactions;

import com.printerapp.domain.aggregates.transaction.Transaction;

public record TransactionRecord(Transaction transaction, UserRecord employee, UserRecord customer) {
}
