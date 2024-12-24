package com.printerapp.application.results.users;

import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEmployeeMailResult {
    private UserId id;
    private Email email;
    private Boolean isActive;
    private String employeeId;
}
