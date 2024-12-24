package com.printerapp.application.exceptions;

public class AppException extends RuntimeException {
    protected Integer code;

    public AppException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}
