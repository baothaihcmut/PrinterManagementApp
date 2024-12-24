package com.printerapp.domain.exceptions.customers;

import com.printerapp.domain.exceptions.common.DomainException;

public class DoNotEnoughPaperException extends DomainException {
    public DoNotEnoughPaperException() {
        super("Don't enoungh paper");
    }
}
