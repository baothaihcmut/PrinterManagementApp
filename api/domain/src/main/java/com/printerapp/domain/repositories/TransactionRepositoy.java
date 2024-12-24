package com.printerapp.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.records.transactions.TransactionRecord;

public interface TransactionRepositoy {
        void save(Transaction transaction);

        List<Transaction> findAll();

        Optional<Transaction> findById(TransactionId transactionId);

        PaginatedResult<TransactionRecord> findByCriteria(List<FilterParam<?>> filters, List<SortParam> sorts,
                        PaginatedParam pagniate);

}
