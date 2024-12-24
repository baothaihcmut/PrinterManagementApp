package com.printerapp.application.mappers;

import com.printerapp.application.results.users.AddEmployeeMailResult;
import com.printerapp.application.results.users.UserResult;
import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.entities.Customer;
import com.printerapp.domain.aggregates.user.entities.Employee;
import com.printerapp.domain.aggregates.user.value_objects.CustomerId;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.domain.enums.Role;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-23T13:33:09+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 23 (Homebrew)"
)
@Component
public class ApplicationUserMapperImpl implements ApplicationUserMapper {

    @Override
    public UserResult toActivateUserResult(User user, Customer customer, Employee employee) {
        if ( user == null && customer == null && employee == null ) {
            return null;
        }

        UserName name = null;
        UserId id = null;
        Email email = null;
        String phoneNumber = null;
        Role role = null;
        Boolean isActive = null;
        if ( user != null ) {
            name = userNameToUserName( user.getName() );
            id = user.getId();
            email = user.getEmail();
            phoneNumber = user.getPhoneNumber();
            role = user.getRole();
            isActive = user.getIsActive();
        }
        UserResult.CustomerResult customer1 = null;
        customer1 = toActivateCustomerResult( customer );
        UserResult.EmployeeResult employee1 = null;
        employee1 = toActivateEmployeeResult( employee );

        UserResult userResult = new UserResult( id, name, email, phoneNumber, role, isActive, customer1, employee1 );

        return userResult;
    }

    @Override
    public UserResult toUserResult(User user) {
        if ( user == null ) {
            return null;
        }

        UserId id = null;
        UserName name = null;
        Email email = null;
        String phoneNumber = null;
        Role role = null;
        Boolean isActive = null;
        UserResult.CustomerResult customer = null;
        UserResult.EmployeeResult employee = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        role = user.getRole();
        isActive = user.getIsActive();
        customer = toActivateCustomerResult( user.getCustomer() );
        employee = toActivateEmployeeResult( user.getEmployee() );

        UserResult userResult = new UserResult( id, name, email, phoneNumber, role, isActive, customer, employee );

        return userResult;
    }

    @Override
    public UserResult.CustomerResult toActivateCustomerResult(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        Map<PaperType, PaperQuantity> paperQuantities = null;
        CustomerId customerId = null;

        Map<PaperType, PaperQuantity> map = customer.getPaperQuantities();
        if ( map != null ) {
            paperQuantities = new LinkedHashMap<PaperType, PaperQuantity>( map );
        }
        customerId = customer.getCustomerId();

        UserResult.CustomerResult customerResult = new UserResult.CustomerResult( paperQuantities, customerId );

        return customerResult;
    }

    @Override
    public UserResult.EmployeeResult toActivateEmployeeResult(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        String employeeId = null;
        LocalDate startWorkDate = null;
        Boolean isOnWork = null;

        employeeId = employee.getEmployeeId();
        startWorkDate = employee.getStartWorkDate();
        isOnWork = employee.getIsOnWork();

        UserResult.EmployeeResult employeeResult = new UserResult.EmployeeResult( employeeId, startWorkDate, isOnWork );

        return employeeResult;
    }

    @Override
    public AddEmployeeMailResult toAddEmployeeMailResult(User user, Employee employee) {
        if ( user == null && employee == null ) {
            return null;
        }

        UserId id = null;
        Email email = null;
        Boolean isActive = null;
        if ( user != null ) {
            id = user.getId();
            email = user.getEmail();
            isActive = user.getIsActive();
        }
        String employeeId = null;
        if ( employee != null ) {
            employeeId = employee.getEmployeeId();
        }

        AddEmployeeMailResult addEmployeeMailResult = new AddEmployeeMailResult( id, email, isActive, employeeId );

        return addEmployeeMailResult;
    }

    protected UserName userNameToUserName(UserName userName) {
        if ( userName == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;

        firstName = userName.getFirstName();
        lastName = userName.getLastName();

        UserName userName1 = new UserName( firstName, lastName );

        return userName1;
    }
}
