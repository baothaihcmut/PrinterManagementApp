package com.printerapp.infrastructure.storage.minio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.printerapp.application.exceptions.StorageException;
import com.printerapp.application.queries.storage.GetPresignUrlGetQuery;
import com.printerapp.application.queries.storage.GetPresignUrlPutQuery;
import com.printerapp.application.results.storage.GetPresignUrlGetResult;
import com.printerapp.application.results.storage.GetPresignUrlPutResult;
import com.printerapp.application.services.StorageService;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MinIOStorageService implements StorageService {
    @Value("${minio.bucket}")
    private String bucket;

    private final MinioClient minioClient;

    @Override
    public GetPresignUrlGetResult getPresignUrlGet(GetPresignUrlGetQuery query) {
        try {
            String url = this.minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(query.getObjectName())
                            .expiry(10, TimeUnit.HOURS)
                            // .extraHeaders(customHeaders)
                            .build());
            return GetPresignUrlGetResult.builder().url(url).build();
        } catch (Exception e) {
            throw new StorageException("Can not get presign link for Get");
        }

    }

    @Override
    public GetPresignUrlPutResult getPresignUrlPut(GetPresignUrlPutQuery query) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String objectName = query.getFileName();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = now.format(formatter);
            objectName = String.format("%s%s.pdf", formattedTime, objectName);
            String url = this.minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucket)
                            .object(objectName)
                            .expiry(10, TimeUnit.HOURS)
                            // .extraQueryParams(customHeaders)
                            .build());
            return GetPresignUrlPutResult.builder().url(url).link(objectName).build();
        } catch (Exception e) {
            throw new StorageException("Can not get link for put");
        }
    }

}
