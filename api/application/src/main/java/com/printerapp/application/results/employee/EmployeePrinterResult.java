package com.printerapp.application.results.employee;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PrinterStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeePrinterResult {
    private PrinterId printerId;
    private Boolean isManager;
    private Integer numOfTransactionProcess;
    private Integer numOfTransactionDone;
    private String printerName;
    private String printerCode;
    private String printerLocation;
    private PrinterStatus printerStatus;
}
