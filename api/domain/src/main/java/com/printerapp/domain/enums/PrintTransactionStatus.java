package com.printerapp.domain.enums;

import lombok.Getter;

@Getter
public enum PrintTransactionStatus {
    PENDING("pending"),
    PROCESS("process"),
    DONE("done"),
    FAILURE("failure"),;

    private String value;

    PrintTransactionStatus(String value) {
        this.value = value;
    }
}
