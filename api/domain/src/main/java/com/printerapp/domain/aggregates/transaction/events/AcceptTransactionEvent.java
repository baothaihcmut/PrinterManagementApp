package com.printerapp.domain.aggregates.transaction.events;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcceptTransactionEvent {
    private UserId employeeId;
    private PrinterId printerId;
}
