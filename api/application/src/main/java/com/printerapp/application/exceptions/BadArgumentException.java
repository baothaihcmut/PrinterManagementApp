package com.printerapp.application.exceptions;

public class BadArgumentException extends AppException {
    public BadArgumentException(String message) {
        super(message, 400);
    }
}
