package com.printerapp.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.printerapp.domain.aggregates.user.entities.Employee;

@Mapper(componentModel = "spring")
public interface InfraEmployeeMapper {
        InfraEmployeeMapper MAPPER = Mappers.getMapper(InfraEmployeeMapper.class);

        @Mappings(value = {
                        @Mapping(source = "id.value", target = "user.id"),
                        @Mapping(source = "id.value", target = "id"),
        })
        @Named("mapEmployeePersistence")
        com.printerapp.infrastructure.persistence.models.Employee toPersistence(Employee employee);

        @Mappings(value = {
                        @Mapping(source = "id", target = "id.value"),
        })
        @Named("mapEmployeeDomain")
        Employee toDomain(com.printerapp.infrastructure.persistence.models.Employee employee);

        // Default methods to handle custom mapping where necessary

}
