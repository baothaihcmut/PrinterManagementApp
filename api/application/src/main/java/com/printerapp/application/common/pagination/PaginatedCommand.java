package com.printerapp.application.common.pagination;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginatedCommand {
    private Integer page;
    private Integer size;
}
