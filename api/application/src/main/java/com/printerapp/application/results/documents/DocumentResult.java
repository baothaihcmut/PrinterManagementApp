package com.printerapp.application.results.documents;

import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.user.value_objects.CustomerId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DocumentResult {
    private DocumentId id;
    private String name;
    private String link;
    private Boolean save;
    private CustomerId customerId;
}
