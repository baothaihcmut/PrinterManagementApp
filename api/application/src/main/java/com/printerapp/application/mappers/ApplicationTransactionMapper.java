package com.printerapp.application.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.application.commands.transactions.CreateTransactionCommand.TransactionDocumentDetail;
import com.printerapp.application.results.transactions.AdminTransactionResult;
import com.printerapp.application.results.transactions.TransactionPrinterResult;
import com.printerapp.application.results.transactions.TransactionResult;
import com.printerapp.application.results.transactions.TransactionUserResult;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.value_objecs.DocumentDetail;
import com.printerapp.domain.aggregates.user.User;

@Mapper(componentModel = "spring")
public interface ApplicationTransactionMapper {
    @Mapping(expression = "java(mapUrls(urls))", target = "urls")
    TransactionResult toResult(Transaction transaction, List<String> urls);

    AdminTransactionResult toAdminTransactionResult(Transaction transaction);

    @Mappings(value = {
            @Mapping(target = "leftSide", defaultValue = "10"),
            @Mapping(target = "rightSide", defaultValue = "10"),
            @Mapping(target = "topSide", defaultValue = "10"),
            @Mapping(target = "bottomSide", defaultValue = "10"),
            @Mapping(target = "numOfPageOneSide", defaultValue = "1"),
            @Mapping(target = "isLandscape", source = "isLandscape")
    })
    DocumentDetail toDocumentDetailDomain(TransactionDocumentDetail detail);

    TransactionUserResult toUserResult(User user);

    TransactionPrinterResult toPrinterResult(Printer printer);

    default List<String> mapUrls(List<String> urls) {
        if (urls == null) {
            return new ArrayList<>();
        }
        return urls;
    }
}
