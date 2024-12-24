package com.printerapp.interfaces.rest.dtos.printers;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePrinterDTO {
    private String name;

    private String location;

    @Pattern(regexp = "ONLINE|OFFLINE|ERROR", message = "Printer Status must be one of these value ONLINE, OFFLINE, ERROR")
    private String status;
}
