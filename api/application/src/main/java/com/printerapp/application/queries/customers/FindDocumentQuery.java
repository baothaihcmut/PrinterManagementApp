package com.printerapp.application.queries.customers;

import com.printerapp.domain.aggregates.user.value_objects.UserId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindDocumentQuery {
    private UserId customerId;
}
