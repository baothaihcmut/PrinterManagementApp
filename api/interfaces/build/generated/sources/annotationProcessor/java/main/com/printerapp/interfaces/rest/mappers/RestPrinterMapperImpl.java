package com.printerapp.interfaces.rest.mappers;

import com.printerapp.application.commands.printers.AddEmployeeToPrinterCommand;
import com.printerapp.application.commands.printers.CreatePrinterCommand;
import com.printerapp.application.commands.printers.UpdatePrinterCommand;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.interfaces.rest.dtos.printers.AddEmployeeToPrinterDTO;
import com.printerapp.interfaces.rest.dtos.printers.CreatePrinterDTO;
import com.printerapp.interfaces.rest.dtos.printers.UpdatePrinterDTO;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T12:42:50+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class RestPrinterMapperImpl implements RestPrinterMapper {

    @Override
    public CreatePrinterCommand toCommand(CreatePrinterDTO dto) {
        if ( dto == null ) {
            return null;
        }

        String name = null;
        String code = null;
        String location = null;
        PrinterStatus status = null;

        name = dto.getName();
        code = dto.getCode();
        location = dto.getLocation();
        if ( dto.getStatus() != null ) {
            status = Enum.valueOf( PrinterStatus.class, dto.getStatus() );
        }

        CreatePrinterCommand createPrinterCommand = new CreatePrinterCommand( name, code, location, status );

        return createPrinterCommand;
    }

    @Override
    public AddEmployeeToPrinterCommand toAddEmployeeToPrinterCommand(AddEmployeeToPrinterDTO dto, UUID printerId) {
        if ( dto == null && printerId == null ) {
            return null;
        }

        AddEmployeeToPrinterCommand.AddEmployeeToPrinterCommandBuilder addEmployeeToPrinterCommand = AddEmployeeToPrinterCommand.builder();

        if ( dto != null ) {
            addEmployeeToPrinterCommand.employeeId( addEmployeeToPrinterDTOToUserId( dto ) );
            addEmployeeToPrinterCommand.isManager( dto.getIsManager() );
        }
        addEmployeeToPrinterCommand.printerId( uUIDToPrinterId( printerId ) );

        return addEmployeeToPrinterCommand.build();
    }

    @Override
    public UpdatePrinterCommand toCommand(UUID id, UpdatePrinterDTO dto) {
        if ( id == null && dto == null ) {
            return null;
        }

        String name = null;
        String location = null;
        PrinterStatus status = null;
        if ( dto != null ) {
            name = dto.getName();
            location = dto.getLocation();
            if ( dto.getStatus() != null ) {
                status = Enum.valueOf( PrinterStatus.class, dto.getStatus() );
            }
        }
        PrinterId id1 = null;
        id1 = uUIDToPrinterId1( id );

        UpdatePrinterCommand updatePrinterCommand = new UpdatePrinterCommand( id1, name, location, status );

        return updatePrinterCommand;
    }

    protected UserId addEmployeeToPrinterDTOToUserId(AddEmployeeToPrinterDTO addEmployeeToPrinterDTO) {
        if ( addEmployeeToPrinterDTO == null ) {
            return null;
        }

        UUID value = null;

        value = addEmployeeToPrinterDTO.getEmployeeId();

        UserId userId = new UserId( value );

        return userId;
    }

    protected PrinterId uUIDToPrinterId(UUID uUID) {
        if ( uUID == null ) {
            return null;
        }

        UUID value = null;

        value = uUID;

        PrinterId printerId = new PrinterId( value );

        return printerId;
    }

    protected PrinterId uUIDToPrinterId1(UUID uUID) {
        if ( uUID == null ) {
            return null;
        }

        UUID value = null;

        value = uUID;

        PrinterId printerId = new PrinterId( value );

        return printerId;
    }
}
