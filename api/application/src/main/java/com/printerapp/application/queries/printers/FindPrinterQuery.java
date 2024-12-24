package com.printerapp.application.queries.printers;

import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrinterStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindPrinterQuery {
    private PrinterStatus status;
    private SortParam sortParam;
    private PaginatedParam paginatedParam;
}
