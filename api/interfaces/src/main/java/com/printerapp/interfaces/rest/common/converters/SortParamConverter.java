package com.printerapp.interfaces.rest.common.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.printerapp.domain.common.sort.Direction;
import com.printerapp.domain.common.sort.SortParam;

public class SortParamConverter implements Converter<String, SortParam> {

    @SuppressWarnings("null")
    @Override
    public SortParam convert(String source) {
        String[] result = source.split(":");
        if (result.length != 2) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wrong sort param");
        }
        return SortParam.builder().field(result[1]).direction(result[0].equals("asc") ? Direction.ASC : Direction.DESC)
                .build();
    }

}
