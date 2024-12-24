package com.printerapp.domain.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseDomainEvent {
    private LocalDateTime createdAt;
}
