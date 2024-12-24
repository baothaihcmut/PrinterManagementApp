package com.printerapp.domain.records.printer_employee;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PrinterStatus;

public record PrinterRecord(PrinterId id, String name, String location, String code, PrinterStatus status) {

}
