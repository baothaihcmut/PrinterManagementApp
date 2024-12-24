package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.Printer;
import com.printerapp.infrastructure.persistence.models.PrinterEmployee;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-23T14:03:30+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 23 (Homebrew)"
)
@Component
public class InfraPrinterEmployeeMapperImpl implements InfraPrinterEmployeeMapper {

    @Override
    public PrinterEmployee toPersistence(com.printerapp.domain.aggregates.printer_employee.PrinterEmployee domain) {
        if ( domain == null ) {
            return null;
        }

        PrinterEmployee printerEmployee = new PrinterEmployee();

        printerEmployee.setPrinter( printerEmployeeIdToPrinter( domain.getId() ) );
        printerEmployee.setEmployee( printerEmployeeIdToEmployee( domain.getId() ) );
        printerEmployee.setId( domain.getPersistenceId() );
        printerEmployee.setCreatedAt( domain.getCreatedAt() );
        printerEmployee.setUpdatedAt( domain.getUpdatedAt() );
        printerEmployee.setIsManager( domain.getIsManager() );
        printerEmployee.setNumOfTransactionProcess( domain.getNumOfTransactionProcess() );
        printerEmployee.setNumOfTransactionDone( domain.getNumOfTransactionDone() );

        return printerEmployee;
    }

    @Override
    public com.printerapp.domain.aggregates.printer_employee.PrinterEmployee toDomain(PrinterEmployee model) {
        if ( model == null ) {
            return null;
        }

        Boolean isManager = null;

        isManager = model.getIsManager();

        UserId employeeId = null;
        PrinterId printerId = null;
        String printerName = null;
        String printerCode = null;
        String printerLocation = null;
        PrinterStatus status = null;

        com.printerapp.domain.aggregates.printer_employee.PrinterEmployee printerEmployee = new com.printerapp.domain.aggregates.printer_employee.PrinterEmployee( employeeId, printerId, isManager, printerName, printerCode, printerLocation, status );

        printerEmployee.setPersistenceId( model.getId() );
        printerEmployee.setNumOfTransactionProcess( model.getNumOfTransactionProcess() );
        printerEmployee.setNumOfTransactionDone( model.getNumOfTransactionDone() );
        printerEmployee.setCreatedAt( model.getCreatedAt() );
        printerEmployee.setUpdatedAt( model.getUpdatedAt() );

        printerEmployee.setId( mapId(model) );

        return printerEmployee;
    }

    private UUID printerEmployeeIdPrinterIdValue(PrinterEmployeeId printerEmployeeId) {
        if ( printerEmployeeId == null ) {
            return null;
        }
        PrinterId printerId = printerEmployeeId.getPrinterId();
        if ( printerId == null ) {
            return null;
        }
        UUID value = printerId.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected Printer printerEmployeeIdToPrinter(PrinterEmployeeId printerEmployeeId) {
        if ( printerEmployeeId == null ) {
            return null;
        }

        Printer printer = new Printer();

        printer.setId( printerEmployeeIdPrinterIdValue( printerEmployeeId ) );

        return printer;
    }

    private UUID printerEmployeeIdEmployeeIdValue(PrinterEmployeeId printerEmployeeId) {
        if ( printerEmployeeId == null ) {
            return null;
        }
        UserId employeeId = printerEmployeeId.getEmployeeId();
        if ( employeeId == null ) {
            return null;
        }
        UUID value = employeeId.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected Employee printerEmployeeIdToEmployee(PrinterEmployeeId printerEmployeeId) {
        if ( printerEmployeeId == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( printerEmployeeIdEmployeeIdValue( printerEmployeeId ) );
        if ( printerEmployeeId.getEmployeeId() != null ) {
            employee.setEmployeeId( map( printerEmployeeId.getEmployeeId() ).toString() );
        }

        return employee;
    }
}
