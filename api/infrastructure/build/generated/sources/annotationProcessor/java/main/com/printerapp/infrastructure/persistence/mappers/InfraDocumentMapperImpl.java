package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.document.Document;
import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.document.value_objects.ObjectLink;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.infrastructure.persistence.models.Customer;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T12:42:55+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class InfraDocumentMapperImpl implements InfraDocumentMapper {

    @Override
    public com.printerapp.infrastructure.persistence.models.Document toPersistence(Document document) {
        if ( document == null ) {
            return null;
        }

        com.printerapp.infrastructure.persistence.models.Document document1 = new com.printerapp.infrastructure.persistence.models.Document();

        document1.setCustomer( userIdToCustomer( document.getCustomerId() ) );
        document1.setId( documentIdValue( document ) );
        document1.setLink( documentLinkValue( document ) );
        document1.setCreatedAt( document.getCreatedAt() );
        document1.setUpdatedAt( document.getUpdatedAt() );
        document1.setName( document.getName() );
        document1.setSave( document.getSave() );

        return document1;
    }

    @Override
    public Document toDomain(com.printerapp.infrastructure.persistence.models.Document model) {
        if ( model == null ) {
            return null;
        }

        UserId customerId = null;
        ObjectLink link = null;
        String name = null;
        Boolean save = null;

        customerId = customerToUserId( model.getCustomer() );
        link = documentToObjectLink( model );
        name = model.getName();
        save = model.getSave();

        Document document = new Document( name, link, save, customerId );

        document.setId( documentToDocumentId( model ) );
        document.setCreatedAt( model.getCreatedAt() );
        document.setUpdatedAt( model.getUpdatedAt() );

        return document;
    }

    protected Customer userIdToCustomer(UserId userId) {
        if ( userId == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setId( userId.getValue() );

        return customer;
    }

    private UUID documentIdValue(Document document) {
        if ( document == null ) {
            return null;
        }
        DocumentId id = document.getId();
        if ( id == null ) {
            return null;
        }
        UUID value = id.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String documentLinkValue(Document document) {
        if ( document == null ) {
            return null;
        }
        ObjectLink link = document.getLink();
        if ( link == null ) {
            return null;
        }
        String value = link.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected DocumentId documentToDocumentId(com.printerapp.infrastructure.persistence.models.Document document) {
        if ( document == null ) {
            return null;
        }

        UUID value = null;

        value = document.getId();

        DocumentId documentId = new DocumentId( value );

        return documentId;
    }

    protected UserId customerToUserId(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        UUID value = null;

        value = customer.getId();

        UserId userId = new UserId( value );

        return userId;
    }

    protected ObjectLink documentToObjectLink(com.printerapp.infrastructure.persistence.models.Document document) {
        if ( document == null ) {
            return null;
        }

        String value = null;

        value = document.getLink();

        ObjectLink objectLink = new ObjectLink( value );

        return objectLink;
    }
}
