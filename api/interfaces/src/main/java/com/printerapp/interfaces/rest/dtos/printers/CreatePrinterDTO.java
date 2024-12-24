package com.printerapp.interfaces.rest.dtos.printers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePrinterDTO {
    @NotNull(message = "Printer name is required")
    private String name;

    @NotNull(message = "Printer code is required")
    private String code;

    @NotNull(message = "Printer location is required")
    private String location;

    @NotNull(message = "Printer Status is required")
    @Pattern(regexp = "ONLINE|OFFLINE|ERROR", message = "Printer Status must be one of these value ONLINE, OFFLINE, ERROR")
    private String status;
}
