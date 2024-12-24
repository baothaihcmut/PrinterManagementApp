package com.printerapp.domain.aggregates.printer;

import java.util.UUID;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.domain.models.BaseAggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Printer extends BaseAggregate<PrinterId> {
    private String name;
    private String code;
    private String location;
    private PrinterStatus status;

    public Printer(String name, String code, String location, PrinterStatus status) {
        super(new PrinterId(UUID.randomUUID()));
        this.name = name;
        this.code = code;
        this.location = location;
        this.status = status;
    }

    // Remove PrinterEmployee by checking its existence

}
