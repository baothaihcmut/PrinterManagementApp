package com.printerapp.application.commands.transactions;

import java.util.List;

import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PaperType;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateTransactionCommand {
    private String name;
    private PrinterId printerId;

    @AllArgsConstructor
    @Data
    public static class TransactionDocumentDetail {
        private PaperType paperType;
        private Integer numOfCopies;
        private Boolean isLandscape;
        private Integer fromPage;
        private Integer toPage;
        private Integer leftSide;
        private Integer rightSide;
        private Integer topSide;
        private Integer bottomSide;
        private Boolean isOneSide;
        private Integer numOfPageOneSide;
    }

    @AllArgsConstructor
    @Data
    public static class OldDocument {
        private DocumentId id;
        private TransactionDocumentDetail detail;
    }

    @AllArgsConstructor
    @Data
    public static class NewDocument {
        private String name;
        private Boolean save;
        private TransactionDocumentDetail detail;
    }

    List<OldDocument> oldDocuments;
    List<NewDocument> newDocuments;
}
