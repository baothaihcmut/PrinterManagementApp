package com.printerapp.interfaces.rest.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppResponse {
    private boolean success;
    private String message;
    private Object data;

    public static ResponseEntity<AppResponse> initRespose(HttpStatus status, Boolean success, String message,
            Object data) {
        return ResponseEntity.status(status).body(new AppResponse(success, message, data));
    }
}
