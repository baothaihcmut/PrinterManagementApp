package com.printerapp.application.queries.storage;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetPresignUrlGetQuery {
    private String objectName;
}
