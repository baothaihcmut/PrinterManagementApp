package com.printerapp.domain.common.sort;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SortParam {
    private String field;
    private Direction direction;
}
