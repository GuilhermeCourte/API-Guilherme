package org.acme.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private final String message;
    private final List<String> details;
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
