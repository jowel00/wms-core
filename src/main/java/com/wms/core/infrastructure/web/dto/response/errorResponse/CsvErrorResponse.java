package com.wms.core.infrastructure.web.dto.response.errorResponse;

import lombok.Getter;

import java.time.Instant;
import java.util.List;
@Getter
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

}
