package com.printerapp.interfaces.rest.mappers;

import com.printerapp.application.commands.users.ActivateUserCommand;
import com.printerapp.application.commands.users.AddEmployeeMailCommand;
import com.printerapp.domain.aggregates.user.value_objects.CustomerId;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.interfaces.rest.dtos.users.ActivateUserDTO;
import com.printerapp.interfaces.rest.dtos.users.AddEmployeeMailDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T12:42:50+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class RestUserMapperImpl implements RestUserMapper {

    @Override
    public ActivateUserCommand toUserInfoCommand(ActivateUserDTO activateUserDTO) {
        if ( activateUserDTO == null ) {
            return null;
        }

        ActivateUserCommand.EmployeeInfo employeeInfo = null;
        String firstName = null;
        String lastName = null;
        String phoneNumber = null;
        ActivateUserCommand.CustomerInfo customerInfo = null;

        employeeInfo = employeeInfoToEmployeeInfo( activateUserDTO.getEmployeeDetail() );
        firstName = activateUserDTO.getFirstName();
        lastName = activateUserDTO.getLastName();
        phoneNumber = activateUserDTO.getPhoneNumber();
        customerInfo = activateUserDTOToCustomerInfo( activateUserDTO );

        ActivateUserCommand activateUserCommand = new ActivateUserCommand( firstName, lastName, phoneNumber, customerInfo, employeeInfo );

        return activateUserCommand;
    }

    @Override
    public AddEmployeeMailCommand toAddEmployeeMailCommand(AddEmployeeMailDTO addMailDTO) {
        if ( addMailDTO == null ) {
            return null;
        }

        List<AddEmployeeMailCommand.AddEmployeeToPrinter> printers = null;

        printers = addEmployeeToPrinterDTOListToAddEmployeeToPrinterList( addMailDTO.getPrinters() );

        Email email = mapMail(addMailDTO.getEmail());

        AddEmployeeMailCommand addEmployeeMailCommand = new AddEmployeeMailCommand( email, printers );

        return addEmployeeMailCommand;
    }

    protected ActivateUserCommand.EmployeeInfo employeeInfoToEmployeeInfo(ActivateUserDTO.EmployeeInfo employeeInfo) {
        if ( employeeInfo == null ) {
            return null;
        }

        String employeeId = null;
        LocalDate startWorkDate = null;

        employeeId = employeeInfo.getEmployeeId();
        startWorkDate = employeeInfo.getStartWorkDate();

        ActivateUserCommand.EmployeeInfo employeeInfo1 = new ActivateUserCommand.EmployeeInfo( employeeId, startWorkDate );

        return employeeInfo1;
    }

    protected ActivateUserCommand.CustomerInfo activateUserDTOToCustomerInfo(ActivateUserDTO activateUserDTO) {
        if ( activateUserDTO == null ) {
            return null;
        }

        CustomerId customerId = mapCustomerInfo(activateUserDTO.getCustomerDetail());

        ActivateUserCommand.CustomerInfo customerInfo = new ActivateUserCommand.CustomerInfo( customerId );

        return customerInfo;
    }

    protected List<AddEmployeeMailCommand.AddEmployeeToPrinter> addEmployeeToPrinterDTOListToAddEmployeeToPrinterList(List<AddEmployeeMailDTO.AddEmployeeToPrinterDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<AddEmployeeMailCommand.AddEmployeeToPrinter> list1 = new ArrayList<AddEmployeeMailCommand.AddEmployeeToPrinter>( list.size() );
        for ( AddEmployeeMailDTO.AddEmployeeToPrinterDTO addEmployeeToPrinterDTO : list ) {
            list1.add( mapPrinters( addEmployeeToPrinterDTO ) );
        }

        return list1;
    }
}
