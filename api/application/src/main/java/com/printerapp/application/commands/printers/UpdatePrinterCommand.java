package com.printerapp.application.commands.printers;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PrinterStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePrinterCommand {
    private PrinterId id;
    private String name;
    private String location;
    private PrinterStatus status;
}
