package com.printerapp.application.results.users;

import java.time.LocalDate;
import java.util.Map;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.user.value_objects.CustomerId;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResult {
    private UserId id;
    private UserName name;
    private Email email;
    private String phoneNumber;
    private Role role;
    private Boolean isActive;

    @AllArgsConstructor
    @Data
    public static class CustomerResult {
        private Map<PaperType, PaperQuantity> paperQuantities;
        private CustomerId customerId;
    }

    @Data
    @AllArgsConstructor
    public static class EmployeeResult {
        private String employeeId;
        private LocalDate startWorkDate;
        private Boolean isOnWork;
    }

    private CustomerResult customer;
    private EmployeeResult employee;
}
