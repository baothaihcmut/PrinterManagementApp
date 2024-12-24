package com.printerapp.application.commands.printers;

import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.enums.PrinterStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindPrinterByStatusCommand {
    PrinterStatus status;
    PaginatedParam paginatedParam;
}
