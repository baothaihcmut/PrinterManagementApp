package com.printerapp.infrastructure.persistence.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.printerapp.domain.enums.CustomerType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "customers")
public class Customer extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerType customerType;

    @Column(nullable = false)
    private Integer numOfPapersA3;

    @Column(nullable = false)
    private Integer numOfPapersA4;

    @Column(nullable = false)
    private Integer numOfPapersA5;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<PrintTransaction> transactions;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Document> documents;

    public Customer(UUID id, Integer numofA3, Integer numOfA4, Integer numOfA5, String customerId,
            CustomerType customerType) {
        this.id = id;
        this.numOfPapersA3 = numofA3;
        this.numOfPapersA4 = numOfA4;
        this.numOfPapersA5 = numOfA5;
        this.customerId = customerId;
        this.customerType = customerType;
    }

}
