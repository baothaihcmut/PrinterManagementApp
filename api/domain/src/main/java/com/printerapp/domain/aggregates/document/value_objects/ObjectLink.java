package com.printerapp.domain.aggregates.document.value_objects;

import java.util.Iterator;
import java.util.List;

import com.printerapp.domain.models.BaseValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectLink extends BaseValueObject {
    private String value;

    @Override
    public Iterator<Object> yieldAttributes() {
        return List.of((Object) value).iterator();
    }

}
