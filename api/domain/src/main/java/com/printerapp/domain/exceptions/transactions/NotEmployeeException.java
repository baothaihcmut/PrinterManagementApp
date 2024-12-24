package com.printerapp.domain.exceptions.transactions;

import com.printerapp.domain.exceptions.common.DomainException;

public class NotEmployeeException extends DomainException {
    public NotEmployeeException() {
        super("Only employee can accept transaction");
    }
}
