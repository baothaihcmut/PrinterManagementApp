package com.printerapp.application.results.storage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPresignUrlPutResult {
    private String link;
    private String url;
}
