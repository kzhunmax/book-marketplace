package com.example.bookmanager.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(String message) {
        this.message = message;
    }
}
