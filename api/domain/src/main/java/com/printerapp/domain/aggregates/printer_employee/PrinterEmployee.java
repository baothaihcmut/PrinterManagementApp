package com.printerapp.domain.aggregates.printer_employee;

import java.util.UUID;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.domain.models.BaseAggregate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrinterEmployee extends BaseAggregate<PrinterEmployeeId> {
    private UUID persistenceId;
    @Setter(AccessLevel.NONE)
    private Boolean isManager;
    private Integer numOfTransactionProcess;
    private Integer numOfTransactionDone;

    public PrinterEmployee(UserId employeeId, PrinterId printerId, Boolean isManager, String printerName,
            String printerCode, String printerLocation, PrinterStatus status) {
        super(new PrinterEmployeeId(employeeId, printerId));
        this.persistenceId = UUID.randomUUID();
        this.numOfTransactionDone = 0;
        this.numOfTransactionProcess = 0;
        this.isManager = isManager;
    }

    public void acceptTransaction() {
        this.numOfTransactionProcess++;
    }

    public void doneTransaction() {
        this.numOfTransactionDone++;
        this.numOfTransactionProcess--;
    }

}
