package com.printerapp.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.application.results.users.AddEmployeeMailResult;
import com.printerapp.application.results.users.UserResult;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.entities.Customer;
import com.printerapp.domain.aggregates.user.entities.Employee;

@Mapper(componentModel = "spring")
public interface ApplicationUserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name.firstName", target = "name.firstName")
    @Mapping(source = "user.name.lastName", target = "name.lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "user.isActive", target = "isActive")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "employee", target = "employee")
    UserResult toActivateUserResult(User user, Customer customer, Employee employee);

    UserResult toUserResult(User user);

    UserResult.CustomerResult toActivateCustomerResult(Customer customer);

    UserResult.EmployeeResult toActivateEmployeeResult(Employee employee);

    @Mappings(value = {
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.isActive", target = "isActive"),
            @Mapping(source = "employee.employeeId", target = "employeeId")

    })
    AddEmployeeMailResult toAddEmployeeMailResult(User user, Employee employee);
}
