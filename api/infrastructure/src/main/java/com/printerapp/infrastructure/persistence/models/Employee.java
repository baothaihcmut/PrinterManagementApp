package com.printerapp.infrastructure.persistence.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "employees")
public class Employee extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private String employeeId;

    @Column(nullable = true)
    private LocalDate startWorkDate;

    @Column(nullable = false)
    private Boolean isOnWork;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<PrinterEmployee> printers;

    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    private List<PrintTransaction> transactions;

    public Employee(UUID id, Boolean isOnWork, LocalDate startWorkDate, String employeeId) {
        this.id = id;
        this.isOnWork = isOnWork;
        this.startWorkDate = startWorkDate;
        this.employeeId = employeeId;
        this.user = new User();
        this.user.setId(id);
    }

}
