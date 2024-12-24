package com.printerapp.infrastructure.google.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeTokenResponse {
    private String access_token;

    private int expires_in;

    private String scope;

    private String token_type;

    private String refresh_token;
}
