package com.printerapp.domain.common.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterParam<T> {
    private String field;
    @Builder.Default
    private Operator operator = Operator.EQUAL;
    private T value;
}
