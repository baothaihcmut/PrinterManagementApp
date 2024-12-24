package com.printerapp.infrastructure.persistence.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.printerapp.domain.enums.PrinterStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "printers")
public class Printer extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrinterStatus status;

    @OneToMany(mappedBy = "printer", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<PrinterEmployee> employees;

    @OneToMany(mappedBy = "printer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PrintTransaction> transactions;

    public Printer(UUID id, String name, String code, String location, PrinterStatus status) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.location = location;
        this.status = status;
    }
}
