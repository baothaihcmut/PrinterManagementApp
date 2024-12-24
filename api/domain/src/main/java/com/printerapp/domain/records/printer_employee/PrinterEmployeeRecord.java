package com.printerapp.domain.records.printer_employee;

import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;

public record PrinterEmployeeRecord(PrinterRecord printer, EmployeeRecord employee,
        PrinterEmployee printerEmployee) {
}
