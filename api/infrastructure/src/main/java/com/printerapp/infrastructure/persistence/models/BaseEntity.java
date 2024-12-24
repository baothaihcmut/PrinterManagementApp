package com.printerapp.infrastructure.persistence.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    protected UUID id;

    @Column(updatable = false)
    protected LocalDateTime createdAt;
    @Column()
    protected LocalDateTime updatedAt;
}
