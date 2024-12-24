package com.printerapp.domain.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseAggregate<TID extends BaseValueObject> extends BaseEntity<TID> {
    protected BaseAggregate(TID id) {
        super(id);
    }
}
