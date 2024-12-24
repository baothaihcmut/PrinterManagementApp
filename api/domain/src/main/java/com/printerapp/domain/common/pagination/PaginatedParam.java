package com.printerapp.domain.common.pagination;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginatedParam {
    private Integer size;
    private Integer page;
}
