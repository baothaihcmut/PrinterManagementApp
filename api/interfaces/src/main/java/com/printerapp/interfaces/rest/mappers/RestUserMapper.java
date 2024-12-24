package com.printerapp.interfaces.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.printerapp.application.commands.users.ActivateUserCommand;
import com.printerapp.application.commands.users.AddEmployeeMailCommand;
import com.printerapp.application.commands.users.AddEmployeeMailCommand.AddEmployeeToPrinter;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.CustomerId;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.enums.CustomerType;
import com.printerapp.domain.enums.Role;
import com.printerapp.interfaces.rest.dtos.users.ActivateUserDTO;
import com.printerapp.interfaces.rest.dtos.users.AddEmployeeMailDTO;
import com.printerapp.interfaces.rest.dtos.users.AddEmployeeMailDTO.AddEmployeeToPrinterDTO;

@Mapper(componentModel = "spring")
public interface RestUserMapper {
    @Mappings(value = {
            @Mapping(expression = "java(mapCustomerInfo(activateUserDTO.getCustomerDetail()))", target = "customerInfo.customerId"),
            @Mapping(source = "employeeDetail", target = "employeeInfo"),
    })
    ActivateUserCommand toUserInfoCommand(ActivateUserDTO activateUserDTO);

    @Mappings(value = {
            @Mapping(expression = "java(mapMail(addMailDTO.getEmail()))", target = "email"),
            @Mapping(source = "printers", target = "printers", qualifiedByName = "mapPrinters")
    })
    AddEmployeeMailCommand toAddEmployeeMailCommand(AddEmployeeMailDTO addMailDTO);

    @Named("mapCustomerType")
    default CustomerType mapCustomerType(String customerType) {
        return CustomerType.valueOf(customerType);
    }

    @Named("mapRole")
    default Role mapRole(String role) {
        return Role.valueOf(role);
    }

    @Named("mapPrinters")
    default AddEmployeeToPrinter mapPrinters(AddEmployeeToPrinterDTO source) {
        return new AddEmployeeToPrinter(new PrinterId(source.getPrinterId()), source.getIsManager());
    }

    default Email mapMail(String email) {
        return new Email(email, Role.EMPLOYEE);
    }

    default CustomerId mapCustomerInfo(ActivateUserDTO.CustomerInfo dto) {
        if (dto == null) {
            return null;
        }

        return new CustomerId(dto.getCustomerId(),
                this.mapCustomerType(dto.getCustomerType()));
    }

}
