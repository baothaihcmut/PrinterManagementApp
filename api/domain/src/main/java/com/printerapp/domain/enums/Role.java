package com.printerapp.domain.enums;

public enum Role {
    CUSTOMER("customer"),
    EMPLOYEE("employee"),
    ADMIN("admin"),
    ;

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
