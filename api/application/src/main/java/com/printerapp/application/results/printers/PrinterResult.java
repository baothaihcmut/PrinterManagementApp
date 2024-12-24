package com.printerapp.application.results.printers;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PrinterStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PrinterResult {
    private PrinterId id;
    private String name;
    private String code;
    private String location;
    private PrinterStatus status;
}
