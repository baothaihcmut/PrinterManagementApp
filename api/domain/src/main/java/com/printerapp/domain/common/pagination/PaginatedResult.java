package com.printerapp.domain.common.pagination;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginatedResult<T> {
    private final List<T> data;
    private final int page; // Current page number (0-based)
    private final int size; // Size of each page
    private final long totalElements; // Total number of elements
    private final int totalPages; // Total number of pages

    public static <T> PaginatedResult<T> of(List<T> data, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return PaginatedResult.<T>builder()
                .data(data)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}
