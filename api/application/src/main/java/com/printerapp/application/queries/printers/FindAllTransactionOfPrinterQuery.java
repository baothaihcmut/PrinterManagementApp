package com.printerapp.application.queries.printers;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllTransactionOfPrinterQuery {
    private PrinterId printerId;
    private PrintTransactionStatus status;
    private PaginatedParam paginatedParam;
    private SortParam sortParam;
}
