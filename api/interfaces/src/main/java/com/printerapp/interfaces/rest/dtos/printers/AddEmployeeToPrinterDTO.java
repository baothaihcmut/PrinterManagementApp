package com.printerapp.interfaces.rest.dtos.printers;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEmployeeToPrinterDTO {
    @NotNull(message = "Employee Id is required")
    private UUID employeeId;

    @NotNull(message = " Is manger is required")
    private Boolean isManager;
}
