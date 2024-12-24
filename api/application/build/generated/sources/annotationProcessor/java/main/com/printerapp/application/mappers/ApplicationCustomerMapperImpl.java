package com.printerapp.application.mappers;

import com.printerapp.application.results.cusomers.CustomerTransactionResult;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.records.transactions.TransactionRecord;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-23T13:33:09+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 23 (Homebrew)"
)
@Component
public class ApplicationCustomerMapperImpl implements ApplicationCustomerMapper {

    @Override
    public CustomerTransactionResult toCustomerTransactionResult(Transaction domain) {
        if ( domain == null ) {
            return null;
        }

        CustomerTransactionResult.CustomerTransactionResultBuilder customerTransactionResult = CustomerTransactionResult.builder();

        customerTransactionResult.id( domain.getId() );
        customerTransactionResult.transactionId( domain.getTransactionId() );
        customerTransactionResult.name( domain.getName() );
        customerTransactionResult.employeeId( domain.getEmployeeId() );
        customerTransactionResult.createdAt( domain.getCreatedAt() );
        customerTransactionResult.status( domain.getStatus() );

        return customerTransactionResult.build();
    }

    @Override
    public CustomerTransactionResult tCustomerTransactionResult(TransactionRecord transactionRecord) {
        if ( transactionRecord == null ) {
            return null;
        }

        CustomerTransactionResult.CustomerTransactionResultBuilder customerTransactionResult = CustomerTransactionResult.builder();

        return customerTransactionResult.build();
    }
}
