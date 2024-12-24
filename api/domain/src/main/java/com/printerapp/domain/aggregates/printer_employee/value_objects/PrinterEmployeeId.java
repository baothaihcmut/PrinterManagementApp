package com.printerapp.domain.aggregates.printer_employee.value_objects;

import java.util.Iterator;
import java.util.List;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.models.BaseValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class PrinterEmployeeId extends BaseValueObject {
    private UserId employeeId;
    private PrinterId printerId;

    @Override
    public Iterator<Object> yieldAttributes() {
        return List.of((Object) employeeId, (Object) printerId).iterator();
    }

}
