package com.printerapp.application.commands.printers;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddEmployeeToPrinterCommand {
    private UserId employeeId;
    private PrinterId printerId;
    private Boolean isManager;
}
