package com.printerapp.domain.aggregates.user.value_objects;

import java.util.Iterator;
import java.util.stream.Stream;

import com.printerapp.domain.enums.Role;
import com.printerapp.domain.models.BaseValueObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Email extends BaseValueObject {
    private String value;

    public Email(String value, Role role) {
        setValue(value, role);
    }

    private void setValue(String value, Role role) {
        if (role.equals(Role.CUSTOMER) && !value.endsWith("@hcmut.edu.vn")) {
            throw new IllegalArgumentException("Customer email must end with @hcmut.edu.vn");
        }
        this.value = value;
    }

    @Override
    public Iterator<Object> yieldAttributes() {
        return Stream.of((Object) this.value).iterator();
    }
}
