package com.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(
            RuntimeException ex
    ) {

        return ResponseEntity
                .internalServerError()
                .body(ex.getMessage());
    }
}