package com.printerapp.application.mappers;

import com.printerapp.application.commands.printers.CreatePrinterCommand;
import com.printerapp.application.results.printers.PrinterResult;
import com.printerapp.application.results.printers.PrinterTransactionResult;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
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
    date = "2024-12-23T13:33:09+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 23 (Homebrew)"
)
@Component
public class ApplicationPrinterMapperImpl implements ApplicationPrinterMapper {

    @Override
    public Printer toDomain(CreatePrinterCommand createPrinterCommand) {
        if ( createPrinterCommand == null ) {
            return null;
        }

        String name = null;
        String code = null;
        String location = null;
        PrinterStatus status = null;

        name = createPrinterCommand.getName();
        code = createPrinterCommand.getCode();
        location = createPrinterCommand.getLocation();
        status = createPrinterCommand.getStatus();

        Printer printer = new Printer( name, code, location, status );

        return printer;
    }

    @Override
    public PrinterResult toResult(Printer printer) {
        if ( printer == null ) {
            return null;
        }

        PrinterResult.PrinterResultBuilder printerResult = PrinterResult.builder();

        printerResult.id( printer.getId() );
        printerResult.name( printer.getName() );
        printerResult.code( printer.getCode() );
        printerResult.location( printer.getLocation() );
        printerResult.status( printer.getStatus() );

        return printerResult.build();
    }

    @Override
    public PrinterTransactionResult toPrinterTransactionResult(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionId id = null;
        String transactionId = null;
        UserId customerId = null;
        UserId employeeId = null;
        PrinterId printerId = null;
        String name = null;
        PrintTransactionStatus status = null;
        LocalDateTime createdAt = null;

        id = transaction.getId();
        transactionId = transaction.getTransactionId();
        customerId = transaction.getCustomerId();
        employeeId = transaction.getEmployeeId();
        printerId = transaction.getPrinterId();
        name = transaction.getName();
        status = transaction.getStatus();
        createdAt = transaction.getCreatedAt();

        UserName customerName = null;
        UserName employeeName = null;

        PrinterTransactionResult printerTransactionResult = new PrinterTransactionResult( id, transactionId, customerId, customerName, employeeId, employeeName, printerId, name, status, createdAt );

        return printerTransactionResult;
    }
}
