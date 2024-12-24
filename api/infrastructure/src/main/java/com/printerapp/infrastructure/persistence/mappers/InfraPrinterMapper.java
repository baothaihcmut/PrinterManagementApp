package com.printerapp.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.infrastructure.persistence.models.Printer;

@Mapper(componentModel = "spring")
public interface InfraPrinterMapper {
        @Mappings(value = {
                        @Mapping(source = "id.value", target = "id"),
        })
        Printer toPersistence(com.printerapp.domain.aggregates.printer.Printer printer);

        @Mappings(value = {
                        @Mapping(source = "id", target = "id.value"),
        })
        com.printerapp.domain.aggregates.printer.Printer toDomain(Printer model);
}
