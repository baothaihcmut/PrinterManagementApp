package com.printerapp.infrastructure.persistence.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.printerapp.domain.enums.PrintTransactionStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "print_transactions")
public class PrintTransaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    @JsonBackReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "printer_id", nullable = true)
    @JsonBackReference
    private Printer printer;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true)
    @JsonBackReference
    private Employee employee;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrintTransactionStatus status;

    @Column(nullable = false)
    private Integer totalNumOfPaperA3;

    @Column(nullable = false)
    private Integer totalNumOfPaperA4;

    @Column(nullable = false)
    private Integer totalNumOfPaperA5;

    @Column(nullable = true)
    private String comment;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TransactionDocument> documents;

    @Column(nullable = true)
    private LocalDateTime acceptedAt;

    @Column(nullable = true)
    private LocalDateTime doneAt;

    public PrintTransaction(UUID id, UUID customerId, String customerFirstname, String customerLastName,
            UUID employeeId, String employeeFirstname, String employeeLastName, UUID printerId, String name,
            PrintTransactionStatus status, String transactionId, Integer totalNumOfPaperA3, Integer totalNumOfPaperA4,
            Integer totalNumOfPaperA5, LocalDateTime createdAt, LocalDateTime updatedAt,
            LocalDateTime acceptedAt, LocalDateTime doneAt) {
        this.id = id;
        this.customer = new Customer();
        this.customer.setUser(new User());
        this.customer.getUser().setFirstName(customerFirstname);
        this.customer.getUser().setLastName(customerLastName);
        this.customer.setId(customerId);
        this.employee = new Employee();
        this.employee.setId(employeeId);
        this.employee.setUser(new User());
        this.employee.getUser().setFirstName(employeeFirstname);
        this.employee.getUser().setLastName(employeeLastName);
        this.printer = new Printer();
        this.printer.setId(printerId);
        this.name = name;
        this.status = status;
        this.transactionId = transactionId;
        this.totalNumOfPaperA3 = totalNumOfPaperA3;
        this.totalNumOfPaperA4 = totalNumOfPaperA4;
        this.totalNumOfPaperA5 = totalNumOfPaperA5;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.acceptedAt = acceptedAt;
        this.doneAt = doneAt;
    }
}
