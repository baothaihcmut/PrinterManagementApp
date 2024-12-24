package com.printerapp.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.records.printer_employee.PrinterEmployeeRecord;

public interface PrinterEmployeeRepository {
    void save(PrinterEmployee printerEmployee);

    Optional<PrinterEmployee> findById(PrinterEmployeeId printerEmployeeId);

    List<PrinterEmployeeRecord> findByIdEmployeeId(UserId employeeId);

    List<PrinterEmployeeRecord> findByPrinterId(PrinterId printerId);

}
