package com.printerapp.infrastructure.persistence.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.printerapp.domain.enums.PaperType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "transaction_documents")
public class TransactionDocument extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaperType paperType;

    @Column(nullable = false)
    private Integer numOfCopies;

    @Column(nullable = false)
    private Boolean isLandscape;

    @Column(nullable = true)
    private Integer fromPage;

    @Column(nullable = true)
    private Integer toPage;

    @Column(nullable = false)
    private Integer leftSide;

    @Column(nullable = false)
    private Integer rightSide;

    @Column(nullable = false)
    private Integer topSide;

    @Column(nullable = false)
    private Integer bottomSide;

    @Column(nullable = false)
    private Boolean isOneSide;

    @Column(nullable = false)
    private Integer numOfPageOneSide;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonBackReference
    private PrintTransaction transaction;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    @JsonBackReference
    private Document document;

    public TransactionDocument(
            UUID id,
            UUID documentId,
            String link,
            String name,
            PaperType paperType,
            Integer numOfCopies,
            Boolean isLandscape,
            Integer fromPage,
            Integer toPage,
            Integer leftSide,
            Integer rightSide,
            Integer topSide,
            Integer bottomSide,
            Boolean isOneSide,
            Integer numOfPageOneSide) {
        this.id = id;
        this.document = new Document();
        this.document.setId(documentId);
        this.document.setLink(link);
        this.document.setName(name);
        this.paperType = paperType;
        this.numOfCopies = numOfCopies;
        this.isLandscape = isLandscape;
        this.fromPage = fromPage;
        this.toPage = toPage;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.topSide = topSide;
        this.bottomSide = bottomSide;
        this.isOneSide = isOneSide;
        this.numOfPageOneSide = numOfPageOneSide;
    }

}
