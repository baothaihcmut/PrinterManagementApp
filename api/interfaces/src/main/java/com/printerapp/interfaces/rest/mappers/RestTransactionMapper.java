package com.printerapp.interfaces.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.printerapp.application.commands.transactions.CreateTransactionCommand;
import com.printerapp.application.commands.transactions.CreateTransactionCommand.NewDocument;
import com.printerapp.application.commands.transactions.CreateTransactionCommand.OldDocument;
import com.printerapp.application.commands.transactions.CreateTransactionCommand.TransactionDocumentDetail;
import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.interfaces.rest.dtos.transactions.CreateTransactionDTO;

@Mapper(componentModel = "spring")
public interface RestTransactionMapper {
    @Mappings(value = {
            @Mapping(source = "printerId", target = "printerId.value"),
            @Mapping(source = "oldDocuments", target = "oldDocuments", qualifiedByName = "mapOldDocuments"),
            @Mapping(source = "newDocuments", target = "newDocuments", qualifiedByName = "mapNewDocument")
    })
    CreateTransactionCommand toCommand(CreateTransactionDTO dto);

    TransactionDocumentDetail toDocumentDetailCommand(
            com.printerapp.interfaces.rest.dtos.transactions.CreateTransactionDTO.TransactionDocumentDetail transactionDocumentDetail);

    @Named("mapOldDocuments")
    default List<OldDocument> mapOldDocuments(
            List<com.printerapp.interfaces.rest.dtos.transactions.CreateTransactionDTO.OldDocument> source) {
        if (source == null) {
            return new ArrayList<>();
        }
        return source.stream().map((document) -> new OldDocument(new DocumentId(document.getId()),
                this.toDocumentDetailCommand(document.getDetail()))).toList();
    }

    @Named("mapNewDocument")
    default List<NewDocument> mapNewDocument(
            List<com.printerapp.interfaces.rest.dtos.transactions.CreateTransactionDTO.NewDocument> source) {
        if (source == null) {
            return new ArrayList<>();
        }
        return source.stream().map((document) -> new NewDocument(document.getName(),
                document.getSave(), this.toDocumentDetailCommand(document.getDetail()))).toList();
    }
}
