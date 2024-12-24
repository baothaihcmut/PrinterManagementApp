package com.printerapp.domain.enums;

import lombok.Getter;

@Getter
public enum PrinterStatus {
    ONLINE("online"),
    OFFLINE("offline"),
    ERROR("error"),;

    private String value;

    PrinterStatus(String value) {
        this.value = value;
    }

}
