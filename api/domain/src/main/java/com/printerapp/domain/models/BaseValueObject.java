package com.printerapp.domain.models;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;

import lombok.Data;

@Data
public abstract class BaseValueObject implements Serializable {

    public abstract Iterator<Object> yieldAttributes();

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BaseValueObject that = (BaseValueObject) obj;
        Iterator<Object> thisAttr = this.yieldAttributes();
        Iterator<Object> thatAttr = that.yieldAttributes();
        while (thisAttr.hasNext() && thatAttr.hasNext()) {
            if (!thisAttr.next().equals(thatAttr.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.yieldAttributes()); // Subclasses can refine this implementation.
    }
}
