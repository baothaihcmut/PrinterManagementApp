package com.printerapp.domain.exceptions.transactions;

import com.printerapp.domain.exceptions.common.DomainException;

public class ExceedDocumentLimitException extends DomainException {
    public ExceedDocumentLimitException(Integer limit) {
        super(String.format("One transaction mustn't have more than %d document", limit));
    }
}
