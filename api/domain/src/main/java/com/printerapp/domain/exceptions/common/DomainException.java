package com.printerapp.domain.exceptions.common;

public abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
