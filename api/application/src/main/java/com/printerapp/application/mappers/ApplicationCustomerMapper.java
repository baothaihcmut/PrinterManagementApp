package com.printerapp.application.mappers;

import org.mapstruct.Mapper;

import com.printerapp.application.results.cusomers.CustomerTransactionResult;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.records.transactions.TransactionRecord;

@Mapper(componentModel = "spring")
public interface ApplicationCustomerMapper {

    // CustomerDocumentResult toDocumCustomerDocumentResult(Document document);

    CustomerTransactionResult toCustomerTransactionResult(Transaction domain);

    CustomerTransactionResult tCustomerTransactionResult(TransactionRecord transactionRecord);
}
