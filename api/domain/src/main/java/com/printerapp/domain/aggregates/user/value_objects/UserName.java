package com.printerapp.domain.aggregates.user.value_objects;

import java.util.Iterator;
import java.util.stream.Stream;

import com.printerapp.domain.models.BaseValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserName extends BaseValueObject {
    private String firstName;
    private String lastName;

    @Override
    public Iterator<Object> yieldAttributes() {
        return Stream.of((Object) firstName, lastName).iterator();
    }

}
