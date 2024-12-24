package com.printerapp.infrastructure.persistence.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "documents")
public class Document extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @Column(nullable = false)
    private Boolean save;

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<TransactionDocument> transactions;

    public Document(UUID id, String name, String link, Boolean save, UUID customerId) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.save = save;
        this.customer = new Customer();
        this.customer.setId(customerId);
    }
}
