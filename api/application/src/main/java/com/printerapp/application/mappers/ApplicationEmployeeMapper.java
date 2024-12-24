package com.printerapp.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.application.results.employee.EmployeePrinterResult;
import com.printerapp.application.results.employee.EmployeeTransactionResult;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.transaction.Transaction;

@Mapper(componentModel = "spring")
public interface ApplicationEmployeeMapper {
    @Mappings(value = {
            @Mapping(source = "id.printerId", target = "printerId"),
    })
    EmployeePrinterResult toEmployeePrinterResult(PrinterEmployee printerEmployee);

    EmployeeTransactionResult toEmployeeTransactionResult(Transaction transaction);

}
