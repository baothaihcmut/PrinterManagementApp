package com.printerapp.domain.aggregates.transaction.value_objecs;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.enums.PaperType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentDetail {
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

    public PaperQuantity calculateTotalPaper() {
        Integer totalPaper = isOneSide ? ((toPage - fromPage + 1) / numOfPageOneSide)
                : ((toPage - fromPage + 1) / (numOfPageOneSide * 2));
        return new PaperQuantity(paperType, totalPaper);
    }
}
