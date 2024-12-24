package com.printerapp.application.handlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.printerapp.application.exceptions.ForbiddenException;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.transaction.events.AcceptTransactionEvent;
import com.printerapp.domain.aggregates.transaction.events.DoneTransactionEvent;
import com.printerapp.domain.repositories.PrinterEmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionEventHandler {
    private final PrinterEmployeeRepository printerEmployeeRepository;

    @EventListener
    public void onAcceptTransaction(AcceptTransactionEvent event) {
        PrinterEmployee printerEmployee = this.printerEmployeeRepository
                .findById(new PrinterEmployeeId(event.getEmployeeId(), event.getPrinterId()))
                .orElseThrow(() -> new ForbiddenException("Employee not in printer"));
        System.out.println(printerEmployee);
        printerEmployee.acceptTransaction();
        this.printerEmployeeRepository.save(printerEmployee);
    }

    @EventListener
    public void onDoneTransaction(DoneTransactionEvent event) {
        PrinterEmployee printerEmployee = this.printerEmployeeRepository
                .findById(new PrinterEmployeeId(event.getEmployeeId(), event.getPrinterId()))
                .orElseThrow(() -> new ForbiddenException("Employee not in printer"));
        printerEmployee.doneTransaction();
        this.printerEmployeeRepository.save(printerEmployee);
    }
}
