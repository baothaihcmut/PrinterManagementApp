package com.printerapp.domain.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Data;

@Data
public class BaseEntity<TID extends BaseValueObject> implements Serializable {
    protected TID id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    protected BaseEntity(TID id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BaseEntity<TID> that = (BaseEntity<TID>) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id); // Subclasses can refine this implementation.
    }

}
