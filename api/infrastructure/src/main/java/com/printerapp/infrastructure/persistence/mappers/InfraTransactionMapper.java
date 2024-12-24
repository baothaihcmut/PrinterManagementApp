package com.printerapp.infrastructure.persistence.mappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.infrastructure.persistence.models.PrintTransaction;
import com.printerapp.infrastructure.persistence.models.TransactionDocument;

@Mapper(componentModel = "spring")
public interface InfraTransactionMapper {

        @Mappings(value = {
                        @Mapping(source = "transactionDocuments", target = "documents", qualifiedByName = "mapDocuments"),
                        @Mapping(source = "id.value", target = "id"),
                        @Mapping(source = "customerId.value", target = "customer.id"),
                        @Mapping(source = "printerId.value", target = "printer.id"),
                        @Mapping(expression = "java(transaction.getEmployeeId()==null ? null : transaction.getEmployeeId().getValue())", target = "employee.id"),
                        @Mapping(expression = "java(mapPaperQuantity(transaction.getTransactionPaperQuantities(),PaperType.A3))", target = "totalNumOfPaperA3"),
                        @Mapping(expression = "java(mapPaperQuantity(transaction.getTransactionPaperQuantities(),PaperType.A4))", target = "totalNumOfPaperA4"),
                        @Mapping(expression = "java(mapPaperQuantity(transaction.getTransactionPaperQuantities(),PaperType.A5))", target = "totalNumOfPaperA5"),
        })
        PrintTransaction toPersistence(Transaction transaction);

        @Mappings(value = {
                        @Mapping(source = "id", target = "id.value"),
                        @Mapping(source = "customer.id", target = "customerId.value"),
                        @Mapping(source = "employee.id", target = "employeeId.value"),
                        @Mapping(source = "printer.id", target = "printerId.value"),
                        @Mapping(expression = "java(mapPaperQuantities(printTransaction))", target = "transactionPaperQuantities")
        })
        Transaction toDomain(PrintTransaction printTransaction);

        @Mappings(value = {
                        @Mapping(target = "id", source = "persistenceId"),
                        @Mapping(target = "document.id", source = "id.value"),
                        @Mapping(target = "paperType", source = "documentDetail.paperType"),
                        @Mapping(target = "numOfCopies", source = "documentDetail.numOfCopies"),
                        @Mapping(target = "isLandscape", source = "documentDetail.isLandscape"),
                        @Mapping(target = "fromPage", source = "documentDetail.fromPage"),
                        @Mapping(target = "toPage", source = "documentDetail.toPage"),
                        @Mapping(target = "leftSide", source = "documentDetail.leftSide"),
                        @Mapping(target = "rightSide", source = "documentDetail.rightSide"),
                        @Mapping(target = "topSide", source = "documentDetail.topSide"),
                        @Mapping(target = "bottomSide", source = "documentDetail.bottomSide"),
                        @Mapping(target = "isOneSide", source = "documentDetail.isOneSide"),
                        @Mapping(target = "numOfPageOneSide", source = "documentDetail.numOfPageOneSide"),
                        @Mapping(target = "transaction", ignore = true),
                        @Mapping(target = "document", ignore = true)
        })
        TransactionDocument toTransactionDocumentPersistence(
                        com.printerapp.domain.aggregates.transaction.entities.TransactionDocument domain);

        @InheritInverseConfiguration
        @Mapping(target = "link.value", source = "document.link")
        @Mapping(target = "name", source = "document.name")
        com.printerapp.domain.aggregates.transaction.entities.TransactionDocument toTransactionDocumentDomain(
                        TransactionDocument model);

        @Named("mapDocuments")
        default List<TransactionDocument> mapDocuments(
                        List<com.printerapp.domain.aggregates.transaction.entities.TransactionDocument> source) {
                return source.stream().map((document) -> this.toTransactionDocumentPersistence(document))
                                .collect(Collectors.toList());
        }

        default Integer mapPaperQuantity(Map<PaperType, PaperQuantity> paperQuantities, PaperType paperType) {
                return paperQuantities.get(paperType).getQuantity();
        }

        default Map<PaperType, PaperQuantity> mapPaperQuantities(PrintTransaction printTransaction) {
                Map<PaperType, PaperQuantity> result = new HashMap<>();
                result.put(PaperType.A3, new PaperQuantity(PaperType.A3, printTransaction.getTotalNumOfPaperA3()));
                result.put(PaperType.A4, new PaperQuantity(PaperType.A4, printTransaction.getTotalNumOfPaperA4()));
                result.put(PaperType.A5, new PaperQuantity(PaperType.A5, printTransaction.getTotalNumOfPaperA5()));
                return result;

        }

}
