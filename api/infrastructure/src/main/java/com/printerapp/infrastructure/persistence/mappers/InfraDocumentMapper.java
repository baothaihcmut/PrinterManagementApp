package com.printerapp.infrastructure.persistence.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.domain.aggregates.document.Document;

@Mapper(componentModel = "spring")
public interface InfraDocumentMapper {

    @Mappings(value = {
            @Mapping(source = "id.value", target = "id"),
            @Mapping(source = "customerId.value", target = "customer.id"),
            @Mapping(source = "link.value", target = "link")
    })
    com.printerapp.infrastructure.persistence.models.Document toPersistence(Document document);

    @InheritInverseConfiguration
    Document toDomain(com.printerapp.infrastructure.persistence.models.Document model);
}
