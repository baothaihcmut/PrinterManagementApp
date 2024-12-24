package com.printerapp.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.repositories.DocumentRepository;
import com.printerapp.infrastructure.persistence.mappers.InfraDocumentMapper;
import com.printerapp.infrastructure.persistence.models.Customer;
import com.printerapp.infrastructure.persistence.models.Document;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JpaDocumentRepository implements DocumentRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final InfraDocumentMapper documentMapper;

    @Override
    public void save(com.printerapp.domain.aggregates.document.Document document) {
        Document documentModel = this.documentMapper.toPersistence(document);
        // set ref
        documentModel.setCustomer(this.entityManager.getReference(Customer.class, documentModel.getCustomer().getId()));
        this.entityManager.merge(documentModel);
    }

    @Override
    public Optional<com.printerapp.domain.aggregates.document.Document> findById(DocumentId id) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> cq = cb.createQuery(Document.class);
        Root<Document> root = cq.from(Document.class);
        cq.select(
                cb.construct(
                        Document.class,
                        root.get("id"),
                        root.get("name"),
                        root.get("link"),
                        root.get("save"),
                        root.get("customer").get("id")))
                .where(cb.equal(root.get("id"), id.getValue()));
        try {
            Document res = this.entityManager.createQuery(cq).getSingleResult();
            // set transaction list
            com.printerapp.domain.aggregates.document.Document resDomain = this.documentMapper.toDomain(res);
            return Optional.of(resDomain);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<com.printerapp.domain.aggregates.document.Document> findAllDocumentOfUser(UserId userId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> cq = cb.createQuery(Document.class);
        Root<Document> root = cq.from(Document.class);
        cq.select(
                cb.construct(
                        Document.class,
                        root.get("id"),
                        root.get("name"),
                        root.get("link"),
                        root.get("save"),
                        root.get("customer").get("id")));
        cq.where(cb.equal(root.get("customer").get("id"), userId.getValue()));
        return this.entityManager.createQuery(cq).getResultList().stream()
                .map((document) -> this.documentMapper.toDomain(document)).toList();
    }

}
