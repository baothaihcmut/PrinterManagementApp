package com.printerapp.application.exceptions;

public class ConflictException extends AppException {

    public ConflictException(String message) {
        super(message, 403);

    }

}
