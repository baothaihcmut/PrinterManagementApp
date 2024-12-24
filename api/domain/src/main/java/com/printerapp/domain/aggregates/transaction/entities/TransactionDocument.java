package com.printerapp.domain.aggregates.transaction.entities;

import java.util.UUID;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.document.value_objects.ObjectLink;
import com.printerapp.domain.aggregates.transaction.value_objecs.DocumentDetail;
import com.printerapp.domain.models.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionDocument extends BaseEntity<DocumentId> {
    private UUID persistenceId;
    private String name;
    private DocumentDetail documentDetail;
    private ObjectLink link;

    public TransactionDocument(DocumentId documentId, String name, DocumentDetail documentDetail, ObjectLink link) {
        super(documentId);
        this.persistenceId = UUID.randomUUID();
        this.name = name;
        this.documentDetail = documentDetail;
        this.link = link;
    }

    public PaperQuantity getTotalPaper() {
        return this.documentDetail.calculateTotalPaper();
    }
}
