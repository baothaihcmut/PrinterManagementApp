package com.printerapp.interfaces.rest.dtos.users;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ActivateUserDTO {
    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotNull(message = "PhoneNumber is required")
    private String phoneNumber;

    @AllArgsConstructor
    @Data
    public static class CustomerInfo {
        @NotNull(message = "Customer Id is required")
        private String customerId;
        @NotNull(message = "Customer type is required")

        @Pattern(regexp = "STUDENT|TEACHER", message = "Customer type must be one of the following: STUDENT, TEACHER")
        private String customerType;
    }

    @AllArgsConstructor
    @Data
    public static class EmployeeInfo {
        @NotNull(message = "Employee Id is required")
        private String employeeId;
        private LocalDate startWorkDate;
    }

    @Valid
    private CustomerInfo customerDetail;

    @Valid
    private EmployeeInfo employeeDetail;
}
