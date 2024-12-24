package com.printerapp.domain.enums;

public enum CustomerType {
    STUDENT("student"),
    TEACHER("teacher");

    private String value;

    CustomerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }
}
