package com.printerapp.application.results.transactions;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PrinterStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionPrinterResult {
    private PrinterId id;
    private String name;
    private String location;
    private String code;
    private PrinterStatus status;
}
