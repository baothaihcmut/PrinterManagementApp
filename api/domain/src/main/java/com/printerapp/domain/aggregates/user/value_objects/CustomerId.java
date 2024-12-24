package com.printerapp.domain.aggregates.user.value_objects;

import java.util.Iterator;
import java.util.List;

import com.printerapp.domain.enums.CustomerType;
import com.printerapp.domain.models.BaseValueObject;

import lombok.Getter;

@Getter
public class CustomerId extends BaseValueObject {
    private CustomerType customerType;
    private String value;

    @Override
    public Iterator<Object> yieldAttributes() {
        return List.of((Object) customerType, (Object) value).iterator();
    }

    public CustomerId(String value, CustomerType customerType) {
        this.customerType = customerType;
        this.value = value;
    }
}
