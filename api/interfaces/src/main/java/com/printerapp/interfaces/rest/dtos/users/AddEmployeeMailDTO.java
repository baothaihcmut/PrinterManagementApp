package com.printerapp.interfaces.rest.dtos.users;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEmployeeMailDTO {
    @AllArgsConstructor
    @Data
    public static class AddEmployeeToPrinterDTO {
        @NotNull(message = "Printer id is required")
        private UUID printerId;
        @NotNull(message = "Is manager is required")
        private Boolean isManager;
    }

    @NotNull(message = "Email is required")
    private String email;

    @Valid
    private List<AddEmployeeToPrinterDTO> printers;
}
