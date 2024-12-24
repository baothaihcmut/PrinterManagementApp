package com.printerapp.application.queries.transactions;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchTransactionQuery {
    private String search;
    private PrintTransactionStatus status;
    private SortParam sortParam;
    private PrinterId printerId;
    private PaginatedParam paginatedParam;

}
