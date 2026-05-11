package com.wms.core.infrastructure.web.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wms.core.domain.exception.CsvParseException;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "code", "message", "path", "timestamp", "errors"})
public class CsvErrorResponse {

    private final int status = 400;
    private final String code = "CSV_VALIDATION_ERROR";
    private final String message;
    private final String path;
    private final Instant timestamp;
    private final List<CsvParseException.CsvRowError> errors;

    public CsvErrorResponse( String message, List<CsvParseException.CsvRowError> errors,  String path) {
        this.message = message;
        this.errors = errors;
        this.path = path;
        this.timestamp = Instant.now();
    }

}
