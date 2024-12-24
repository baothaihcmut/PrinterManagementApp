package com.printerapp.domain.aggregates.user.entities;

import java.util.HashMap;
import java.util.Map;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.user.value_objects.CustomerId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.domain.models.BaseAggregate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseAggregate<UserId> {
        private CustomerId customerId;
        @Setter(AccessLevel.NONE)
        private Map<PaperType, PaperQuantity> paperQuantities;

        public Customer(UserId userId, CustomerId customerId) {
                super(userId);
                this.customerId = customerId;
                this.paperQuantities = new HashMap<>();
                // set default paper for user
                this.paperQuantities.put(PaperType.A3, new PaperQuantity(PaperType.A3, 100));
                this.paperQuantities.put(PaperType.A4, new PaperQuantity(PaperType.A4, 100));
                this.paperQuantities.put(PaperType.A5, new PaperQuantity(PaperType.A5, 100));

        }

        public void addPaper(PaperQuantity quanity) {
                PaperQuantity current = this.paperQuantities.get(quanity.getType());
                this.paperQuantities.put(quanity.getType(), current.add(quanity));

        }

        public void subtractPaper(PaperQuantity quantity) throws IllegalArgumentException {
                PaperQuantity currentPaperQuantity = this.paperQuantities.get(quantity.getType());
                if (currentPaperQuantity.getQuantity() < quantity.getQuantity()) {
                        throw new IllegalArgumentException("Don't enoungh paper");
                }
                this.paperQuantities.put(quantity.getType(), currentPaperQuantity.subtract(quantity));
        }

}
