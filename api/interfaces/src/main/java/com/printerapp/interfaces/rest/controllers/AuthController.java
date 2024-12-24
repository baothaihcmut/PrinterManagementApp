package com.printerapp.interfaces.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.printerapp.application.commands.auth.ExchangeTokenCommand;
import com.printerapp.application.results.auth.ExchangeTokenResult;
import com.printerapp.application.services.AuthService;
import com.printerapp.interfaces.rest.common.response.AppResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/exchangeToken")
    public ResponseEntity<AppResponse> exchangeToken(@RequestHeader("gid") String gid) throws Exception {
        System.out.println(gid);
        ExchangeTokenResult res = this.authService.exchangeToken(ExchangeTokenCommand.builder().token(gid).build());
        return AppResponse.initRespose(HttpStatus.CREATED, true, "Exchange token success", res);
    }
}
