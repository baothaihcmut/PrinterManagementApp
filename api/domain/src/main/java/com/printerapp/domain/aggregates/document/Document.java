package com.printerapp.domain.aggregates.document;

import java.util.UUID;

import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.document.value_objects.ObjectLink;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.models.BaseAggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Document extends BaseAggregate<DocumentId> {
    private String name;
    private ObjectLink link;
    private Boolean save;
    private UserId customerId;

    public Document(String name, ObjectLink link, Boolean save, UserId customerId) {
        super(new DocumentId(UUID.randomUUID()));
        this.name = name;
        this.link = link;
        this.save = save;
        this.customerId = customerId;
    }
}
