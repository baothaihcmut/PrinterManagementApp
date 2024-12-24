package com.printerapp.application.exceptions;

public class StorageException extends AppException {
    public StorageException(String message) {
        super(message, 500);
    }
}
