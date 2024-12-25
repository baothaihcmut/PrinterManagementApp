package com.printerapp.interfaces.rest.mappers;

import com.printerapp.application.commands.transactions.CreateTransactionCommand;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.interfaces.rest.dtos.transactions.CreateTransactionDTO;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T12:42:50+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class RestTransactionMapperImpl implements RestTransactionMapper {

    @Override
    public CreateTransactionCommand toCommand(CreateTransactionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PrinterId printerId = null;
        List<CreateTransactionCommand.OldDocument> oldDocuments = null;
        List<CreateTransactionCommand.NewDocument> newDocuments = null;
        String name = null;

        printerId = createTransactionDTOToPrinterId( dto );
        oldDocuments = mapOldDocuments( dto.getOldDocuments() );
        newDocuments = mapNewDocument( dto.getNewDocuments() );
        name = dto.getName();

        CreateTransactionCommand createTransactionCommand = new CreateTransactionCommand( name, printerId, oldDocuments, newDocuments );

        return createTransactionCommand;
    }

    @Override
    public CreateTransactionCommand.TransactionDocumentDetail toDocumentDetailCommand(CreateTransactionDTO.TransactionDocumentDetail transactionDocumentDetail) {
        if ( transactionDocumentDetail == null ) {
            return null;
        }

        PaperType paperType = null;
        Integer numOfCopies = null;
        Boolean isLandscape = null;
        Integer fromPage = null;
        Integer toPage = null;
        Integer leftSide = null;
        Integer rightSide = null;
        Integer topSide = null;
        Integer bottomSide = null;
        Boolean isOneSide = null;
        Integer numOfPageOneSide = null;

        if ( transactionDocumentDetail.getPaperType() != null ) {
            paperType = Enum.valueOf( PaperType.class, transactionDocumentDetail.getPaperType() );
        }
        numOfCopies = transactionDocumentDetail.getNumOfCopies();
        isLandscape = transactionDocumentDetail.getIsLandscape();
        fromPage = transactionDocumentDetail.getFromPage();
        toPage = transactionDocumentDetail.getToPage();
        leftSide = transactionDocumentDetail.getLeftSide();
        rightSide = transactionDocumentDetail.getRightSide();
        topSide = transactionDocumentDetail.getTopSide();
        bottomSide = transactionDocumentDetail.getBottomSide();
        isOneSide = transactionDocumentDetail.getIsOneSide();
        numOfPageOneSide = transactionDocumentDetail.getNumOfPageOneSide();

        CreateTransactionCommand.TransactionDocumentDetail transactionDocumentDetail1 = new CreateTransactionCommand.TransactionDocumentDetail( paperType, numOfCopies, isLandscape, fromPage, toPage, leftSide, rightSide, topSide, bottomSide, isOneSide, numOfPageOneSide );

        return transactionDocumentDetail1;
    }

    protected PrinterId createTransactionDTOToPrinterId(CreateTransactionDTO createTransactionDTO) {
        if ( createTransactionDTO == null ) {
            return null;
        }

        UUID value = null;

        value = createTransactionDTO.getPrinterId();

        PrinterId printerId = new PrinterId( value );

        return printerId;
    }
}
