package com.wms.core.infrastructure.web.exception;

import java.time.Instant;
import java.util.List;

public class CsvErrorResponse {

    private final Instant timestamp = Instant.now();
    private final int status = 400;
    private final String error = "Bad Request";
    private final String message;
    private final List<String> errors;
    private final String path;

    public CsvErrorResponse(String message, List<String> errors, String path) {
        this.message = message;
        this.errors = errors;
        this.path = path;
    }

    public Instant getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public List<String> getErrors() { return errors; }
    public String getPath() { return path; }
}
