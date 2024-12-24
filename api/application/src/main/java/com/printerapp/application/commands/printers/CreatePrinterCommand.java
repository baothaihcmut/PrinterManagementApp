package com.printerapp.application.commands.printers;

import com.printerapp.domain.enums.PrinterStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePrinterCommand {
    private String name;
    private String code;
    private String location;
    private PrinterStatus status;
}
