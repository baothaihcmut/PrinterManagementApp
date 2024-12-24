package com.printerapp.application.queries.documents;

import com.printerapp.domain.aggregates.document.value_objects.DocumentId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindDocumentByIdQuery {
    DocumentId documentId;
}
