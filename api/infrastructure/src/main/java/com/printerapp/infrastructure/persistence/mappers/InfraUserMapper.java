package com.printerapp.infrastructure.persistence.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.entities.Customer;
import com.printerapp.domain.aggregates.user.entities.Employee;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.enums.Role;

@Mapper(componentModel = "spring", uses = { InfraCustomerMapper.class, InfraEmployeeMapper.class })
public interface InfraUserMapper {
    InfraUserMapper MAPPER = Mappers.getMapper(InfraUserMapper.class);

    @Mappings(value = {
            @Mapping(source = "id.value", target = "id"),
            @Mapping(source = "email.value", target = "email"),
            @Mapping(source = "name.firstName", target = "firstName"),
            @Mapping(source = "name.lastName", target = "lastName"),
            @Mapping(source = "customer", target = "customer", qualifiedByName = "mapCustomerPersistence"),
            @Mapping(source = "employee", target = "employee", qualifiedByName = "mapEmployeePersistence")
    })
    com.printerapp.infrastructure.persistence.models.User toPersistenct(User user);

    @Mappings(value = {
            @Mapping(source = "id", target = "id.value"),
            @Mapping(target = "name.firstName", source = "firstName"),
            @Mapping(target = "name.lastName", source = "lastName"),
            @Mapping(expression = "java(mapEmail(model.getEmail(),model.getRole()))", target = "email"),
            @Mapping(source = "customer", target = "customer", qualifiedByName = "mapCustomerDomain"),
            @Mapping(source = "employee", target = "employee", qualifiedByName = "mapEmployeeDomain"),
    })
    User toDomain(com.printerapp.infrastructure.persistence.models.User model);

    default Email mapEmail(String email, Role role) {
        return new Email(email, role);
    }

    default Customer mapCustomer(com.printerapp.infrastructure.persistence.models.Customer entity,
            @Context InfraCustomerMapper customerMapper) {
        if (entity == null) {
            return null;
        }
        return customerMapper.toDomain(entity);
    }

    default Employee mapEmployeeDomain(com.printerapp.infrastructure.persistence.models.User entity,
            @Context InfraEmployeeMapper employeeMapper) {
        if (!entity.getRole().equals(Role.EMPLOYEE)) {
            return null;
        }
        return employeeMapper.toDomain(entity.getEmployee());
    }

}
