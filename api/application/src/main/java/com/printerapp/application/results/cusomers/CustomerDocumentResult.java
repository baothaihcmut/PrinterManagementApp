package com.printerapp.application.results.cusomers;

import com.printerapp.domain.aggregates.document.value_objects.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDocumentResult {
    private DocumentId id;
    private String name;
    private String link;
}
