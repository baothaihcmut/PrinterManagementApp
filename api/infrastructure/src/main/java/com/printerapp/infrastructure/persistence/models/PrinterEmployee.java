package com.printerapp.infrastructure.persistence.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.printerapp.domain.enums.PrinterStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "printer_employees")
public class PrinterEmployee extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "printer_id", nullable = false)
    @JsonBackReference
    private Printer printer;

    @Column(nullable = false)
    private Boolean isManager;

    @Column(nullable = false)
    private Integer numOfTransactionProcess;

    @Column(nullable = false)
    private Integer numOfTransactionDone;

    public PrinterEmployee(
            UUID id,
            UUID userId,
            String firstName, String lastName, String email,
            UUID printerId, String name, String location, String code,
            PrinterStatus status,
            Boolean isManger, Integer numOfTransactionProcess,
            Integer numOfTransactionDone) {
        this.id = id;
        this.employee = new Employee();
        this.employee.setId(userId);
        this.employee.setUser(new User());
        this.employee.getUser().setFirstName(firstName);
        this.employee.getUser().setLastName(lastName);
        this.employee.getUser().setEmail(email);
        this.employee.getUser().setEmployee(this.employee);
        this.printer = new Printer();
        this.printer.setId(printerId);
        this.printer.setName(name);
        this.printer.setCode(code);
        this.printer.setLocation(location);
        this.printer.setStatus(status);
        this.setIsManager(isManger);
        this.numOfTransactionProcess = numOfTransactionProcess;
        this.numOfTransactionDone = numOfTransactionDone;
    }

}
