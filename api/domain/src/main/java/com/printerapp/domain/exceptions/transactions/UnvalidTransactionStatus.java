package com.printerapp.domain.exceptions.transactions;

import com.printerapp.domain.exceptions.common.DomainException;

public class UnvalidTransactionStatus extends DomainException {
    public UnvalidTransactionStatus(String message) {
        super(message);
    }
}
