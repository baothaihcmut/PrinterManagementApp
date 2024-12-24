package com.printerapp.infrastructure.persistence.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.infrastructure.persistence.models.PrinterEmployee;

@Mapper(componentModel = "spring")
public interface InfraPrinterEmployeeMapper {
    @Mappings(value = {
            @Mapping(source = "persistenceId", target = "id"),
            @Mapping(source = "id.printerId.value", target = "printer.id"),
            @Mapping(source = "id.employeeId.value", target = "employee.id"),
    })
    PrinterEmployee toPersistence(com.printerapp.domain.aggregates.printer_employee.PrinterEmployee domain);

    @Mappings(value = {
            @Mapping(target = "persistenceId", source = "id"),
            @Mapping(target = "id", expression = "java(mapId(model))"),
            @Mapping(target = "isManager", source = "isManager"),
            @Mapping(target = "numOfTransactionProcess", source = "numOfTransactionProcess"),
            @Mapping(target = "numOfTransactionDone", source = "numOfTransactionDone"),
    })
    com.printerapp.domain.aggregates.printer_employee.PrinterEmployee toDomain(PrinterEmployee model);

    default PrinterId map(UUID value) {
        return new PrinterId(value);
    }

    default UUID map(PrinterId printerId) {
        return printerId.getValue();
    }

    default UserId mapUser(UUID value) {
        return new UserId(value);
    }

    default UUID map(UserId userId) {
        return userId.getValue();
    }

    default PrinterEmployeeId mapId(PrinterEmployee printerEmployee) {
        return new PrinterEmployeeId(new UserId(printerEmployee.getEmployee().getId()),
                new PrinterId(printerEmployee.getPrinter().getId()));
    }
}
