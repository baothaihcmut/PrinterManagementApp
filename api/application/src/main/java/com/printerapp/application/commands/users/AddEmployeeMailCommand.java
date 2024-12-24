package com.printerapp.application.commands.users;

import java.util.List;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.Email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEmployeeMailCommand {
    @Data
    @AllArgsConstructor
    public static class AddEmployeeToPrinter {
        PrinterId printerId;
        Boolean isManger;
    }

    Email email;
    List<AddEmployeeToPrinter> printers;

}
