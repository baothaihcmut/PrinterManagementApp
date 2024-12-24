package com.printerapp.domain.records.printer_employee;

import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;

public record EmployeeRecord(UserId id, UserName name, Email email) {

}
