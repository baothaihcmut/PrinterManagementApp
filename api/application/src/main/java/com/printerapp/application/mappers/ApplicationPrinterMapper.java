package com.printerapp.application.mappers;

import org.mapstruct.Mapper;

import com.printerapp.application.commands.printers.CreatePrinterCommand;
import com.printerapp.application.results.printers.PrinterResult;
import com.printerapp.application.results.printers.PrinterTransactionResult;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.transaction.Transaction;

@Mapper(componentModel = "spring")
public interface ApplicationPrinterMapper {
    Printer toDomain(CreatePrinterCommand createPrinterCommand);

    PrinterResult toResult(Printer printer);

    PrinterTransactionResult toPrinterTransactionResult(Transaction transaction);
}
