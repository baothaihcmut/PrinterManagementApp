package com.printerapp.application.commands.users;

import java.time.LocalDate;

import com.printerapp.domain.aggregates.user.value_objects.CustomerId;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ActivateUserCommand {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private CustomerInfo customerInfo;
    private EmployeeInfo employeeInfo;

    @AllArgsConstructor
    @Data
    public static class CustomerInfo {
        private CustomerId customerId;
    }

    @AllArgsConstructor
    @Data
    public static class EmployeeInfo {
        private String employeeId;
        private LocalDate startWorkDate;
    }

}
