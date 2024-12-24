package com.printerapp.interfaces.rest.common.handlers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.printerapp.application.exceptions.AppException;
import com.printerapp.interfaces.rest.common.response.AppResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppResponse> handleAppException(AppException e) {
        return AppResponse.initRespose(HttpStatus.valueOf(e.getCode()), false, e.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppResponse> handleDTOException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return AppResponse.initRespose(HttpStatus.BAD_REQUEST, false, "Unvalid payload", errorMap);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<AppResponse> handleAuthException(AuthorizationDeniedException e) {
        return AppResponse.initRespose(HttpStatus.FORBIDDEN, false, "You don't have permission access this resource",
                null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<AppResponse> handleNoHandlerFound(NoHandlerFoundException e) {
        return AppResponse.initRespose(HttpStatus.NOT_FOUND, true, "Handler not found", null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return AppResponse.initRespose(HttpStatus.INTERNAL_SERVER_ERROR, false, "Internal server error", null);
    }
}
