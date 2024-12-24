package com.printerapp.interfaces.rest.dtos.transactions;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class TransactionDocumentDetail {

        @Pattern(regexp = "A3|A4|A5", message = "Paper type must be A3, A4,A5")
        @NotNull(message = "Paper type is required")
        private String paperType;

        @NotNull(message = "Num of copies is required")
        private int numOfCopies;

        @NotNull(message = "Is landscape is required")
        private Boolean isLandscape;

        private Integer fromPage;

        private Integer toPage;

        private Integer leftSide;

        private Integer rightSide;

        private Integer topSide;

        private Integer bottomSide;

        @NotNull(message = "Is one side is required")
        private Boolean isOneSide;

        private Integer numOfPageOneSide;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OldDocument {
        @NotNull(message = "Document id is required")
        private UUID id;
        @NotNull(message = "Transaction document detail is required")
        @Valid
        private TransactionDocumentDetail detail;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class NewDocument {
        @NotNull(message = "Name is required")
        private String name;
        @NotNull(message = "Save is required")
        private Boolean save;

        @NotNull(message = "Transaction document detail is required")
        @Valid
        private TransactionDocumentDetail detail;
    }

    @NotNull(message = "Printer id required")
    private UUID printerId;

    @NotNull(message = "Name is required")
    private String name;

    @Valid
    List<OldDocument> oldDocuments;

    @Valid
    List<NewDocument> newDocuments;
}
