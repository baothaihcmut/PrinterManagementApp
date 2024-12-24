package com.printerapp.domain.aggregates.transaction.value_objecs;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.printerapp.domain.models.BaseValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TransactionId extends BaseValueObject {
    private UUID value;

    @Override
    public Iterator<Object> yieldAttributes() {
        return List.of((Object) value).iterator();
    }
}
