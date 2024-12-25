package com.printerapp.application.mappers;

import com.printerapp.application.results.employee.EmployeePrinterResult;
import com.printerapp.application.results.employee.EmployeeTransactionResult;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.enums.PrinterStatus;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T12:42:41+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ApplicationEmployeeMapperImpl implements ApplicationEmployeeMapper {

    @Override
    public EmployeePrinterResult toEmployeePrinterResult(PrinterEmployee printerEmployee) {
        if ( printerEmployee == null ) {
            return null;
        }

        PrinterId printerId = null;
        Boolean isManager = null;
        Integer numOfTransactionProcess = null;
        Integer numOfTransactionDone = null;

        printerId = printerEmployeeIdPrinterId( printerEmployee );
        isManager = printerEmployee.getIsManager();
        numOfTransactionProcess = printerEmployee.getNumOfTransactionProcess();
        numOfTransactionDone = printerEmployee.getNumOfTransactionDone();

        String printerName = null;
        String printerCode = null;
        String printerLocation = null;
        PrinterStatus printerStatus = null;

        EmployeePrinterResult employeePrinterResult = new EmployeePrinterResult( printerId, isManager, numOfTransactionProcess, numOfTransactionDone, printerName, printerCode, printerLocation, printerStatus );

        return employeePrinterResult;
    }

    @Override
    public EmployeeTransactionResult toEmployeeTransactionResult(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionId id = null;
        String transactionId = null;
        String name = null;
        UserId customerId = null;
        LocalDateTime createdAt = null;
        PrintTransactionStatus status = null;

        id = transaction.getId();
        transactionId = transaction.getTransactionId();
        name = transaction.getName();
        customerId = transaction.getCustomerId();
        createdAt = transaction.getCreatedAt();
        status = transaction.getStatus();

        UserName customerName = null;

        EmployeeTransactionResult employeeTransactionResult = new EmployeeTransactionResult( id, transactionId, name, customerId, customerName, createdAt, status );

        return employeeTransactionResult;
    }

    private PrinterId printerEmployeeIdPrinterId(PrinterEmployee printerEmployee) {
        if ( printerEmployee == null ) {
            return null;
        }
        PrinterEmployeeId id = printerEmployee.getId();
        if ( id == null ) {
            return null;
        }
        PrinterId printerId = id.getPrinterId();
        if ( printerId == null ) {
            return null;
        }
        return printerId;
    }
}
