package com.printerapp.infrastructure.persistence.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class PrinterEmployeeId implements Serializable {
    private UUID employeeid;

    private UUID printerId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PrinterEmployeeId that = (PrinterEmployeeId) o;
        return Objects.equals(employeeid, that.employeeid) && Objects.equals(printerId, that.printerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeid, printerId);
    }
}
