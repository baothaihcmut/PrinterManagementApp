package com.printerapp.application.results.auth;

import com.printerapp.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExchangeTokenResult {
    Boolean isActive;
    Role role;
    Token token;

    @AllArgsConstructor
    @Data
    public static class Token {
        private String accessToken;
        private String refreshToken;
    }
}
