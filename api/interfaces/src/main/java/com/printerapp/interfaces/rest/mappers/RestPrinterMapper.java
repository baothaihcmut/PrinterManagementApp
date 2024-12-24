package com.printerapp.interfaces.rest.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.application.commands.printers.AddEmployeeToPrinterCommand;
import com.printerapp.application.commands.printers.CreatePrinterCommand;
import com.printerapp.application.commands.printers.UpdatePrinterCommand;
import com.printerapp.interfaces.rest.dtos.printers.AddEmployeeToPrinterDTO;
import com.printerapp.interfaces.rest.dtos.printers.CreatePrinterDTO;
import com.printerapp.interfaces.rest.dtos.printers.UpdatePrinterDTO;

@Mapper(componentModel = "spring")
public interface RestPrinterMapper {
    CreatePrinterCommand toCommand(CreatePrinterDTO dto);

    @Mappings(value = {
            @Mapping(source = "dto.employeeId", target = "employeeId.value"),
            @Mapping(source = "dto.isManager", target = "isManager"),
            @Mapping(source = "printerId", target = "printerId.value")
    })
    AddEmployeeToPrinterCommand toAddEmployeeToPrinterCommand(AddEmployeeToPrinterDTO dto, UUID printerId);

    @Mappings(value = {
            @Mapping(source = "id", target = "id.value"),
            @Mapping(source = "dto.name", target = "name"),
            @Mapping(source = "dto.location", target = "location"),
            @Mapping(source = "dto.status", target = "status")
    })
    UpdatePrinterCommand toCommand(UUID id, UpdatePrinterDTO dto);

}
