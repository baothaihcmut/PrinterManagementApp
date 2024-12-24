package com.printerapp.application.commands.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeTokenCommand {
    private String token;
}
