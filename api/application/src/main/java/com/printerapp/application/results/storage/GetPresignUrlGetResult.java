package com.printerapp.application.results.storage;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetPresignUrlGetResult {
    private String url;
}
