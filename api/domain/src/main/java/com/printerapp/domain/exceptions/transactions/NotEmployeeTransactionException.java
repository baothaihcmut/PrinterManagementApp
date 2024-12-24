package com.printerapp.domain.exceptions.transactions;

import com.printerapp.domain.exceptions.common.DomainException;

public class NotEmployeeTransactionException extends DomainException {

    public NotEmployeeTransactionException() {
        super("You can't done this transaction");
    }
}
