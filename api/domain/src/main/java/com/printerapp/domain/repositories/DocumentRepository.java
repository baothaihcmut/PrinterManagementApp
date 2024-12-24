package com.printerapp.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.printerapp.domain.aggregates.document.Document;
import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;

public interface DocumentRepository {
    void save(Document document);

    Optional<Document> findById(DocumentId id);

    List<Document> findAllDocumentOfUser(UserId userId);

}
