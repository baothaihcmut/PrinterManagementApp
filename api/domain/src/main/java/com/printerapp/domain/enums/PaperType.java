package com.printerapp.domain.enums;

public enum PaperType {
    A3("A3"),
    A4("A4"),
    A5("A5"),;

    private String value;

    PaperType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }
}
