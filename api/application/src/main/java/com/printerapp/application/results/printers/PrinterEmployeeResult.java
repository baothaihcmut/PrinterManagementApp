package com.printerapp.application.results.printers;

import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrinterEmployeeResult {
    private UserId id;
    private String firstName;
    private String lastName;
    private Email email;
}
