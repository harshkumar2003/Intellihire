package com.intellihire.authService.DTO.Response;

import java.time.Instant;
import java.util.Map;

public class ErrorResponse {

    private final String errorCode;
    private final String message;
    private final int status;
    private final Instant timestamp;
    private final Map<String, String> errors;

    // For non-validation errors
    public ErrorResponse(String errorCode, String message, int status) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.timestamp = Instant.now();
        this.errors = null;
    }

    // For validation errors
    public ErrorResponse(
            String errorCode,
            String message,
            int status,
            Map<String, String> errors
    ) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.errors = errors;
        this.timestamp = Instant.now();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
