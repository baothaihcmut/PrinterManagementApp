package com.printerapp.application.services;

import com.printerapp.application.queries.storage.GetPresignUrlGetQuery;
import com.printerapp.application.queries.storage.GetPresignUrlPutQuery;
import com.printerapp.application.results.storage.GetPresignUrlGetResult;
import com.printerapp.application.results.storage.GetPresignUrlPutResult;

public interface StorageService {
    GetPresignUrlGetResult getPresignUrlGet(GetPresignUrlGetQuery query);

    GetPresignUrlPutResult getPresignUrlPut(GetPresignUrlPutQuery query);
}
