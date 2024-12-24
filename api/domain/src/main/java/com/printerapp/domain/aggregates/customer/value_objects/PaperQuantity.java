package com.printerapp.domain.aggregates.customer.value_objects;

import com.printerapp.domain.enums.PaperType;

import lombok.Getter;

public class PaperQuantity {
    @Getter
    private PaperType type;
    @Getter
    private Integer quantity;

    public PaperQuantity(PaperType paperType, Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity of paper must greater than 0");
        }
        this.quantity = quantity;
        this.type = paperType;
    }

    public PaperQuantity add(PaperQuantity paperQuantity) {
        if (!this.type.equals(paperQuantity.getType())) {
            throw new IllegalArgumentException("Wrong type of paper");
        }
        return new PaperQuantity(type, quantity + paperQuantity.getQuantity());
    }

    public PaperQuantity subtract(PaperQuantity paperQuantity) {
        if (!this.type.equals(paperQuantity.getType())) {
            throw new IllegalArgumentException("Wrong type of paper");
        }
        if (this.quantity < paperQuantity.getQuantity()) {
            throw new IllegalArgumentException();
        }
        return new PaperQuantity(type, quantity - paperQuantity.getQuantity());
    }
}
