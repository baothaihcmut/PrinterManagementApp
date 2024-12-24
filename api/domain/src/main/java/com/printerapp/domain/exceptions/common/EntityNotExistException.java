package com.printerapp.domain.exceptions.common;

public class EntityNotExistException extends DomainException {
    public EntityNotExistException(Class<?> cls) {
        super(String.format("%s not found", cls.getName()));
    }
}
