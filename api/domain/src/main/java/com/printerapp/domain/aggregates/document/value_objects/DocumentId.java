package com.printerapp.domain.aggregates.document.value_objects;

import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;

import com.printerapp.domain.models.BaseValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class DocumentId extends BaseValueObject {
    private UUID value;

    @Override
    public Iterator<Object> yieldAttributes() {
        return Stream.of((Object) this.value).iterator();
    }
}
