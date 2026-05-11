package com.wms.core.infrastructure.web.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;
    private final String path;
    private final Instant timestamp;
    private final List<FieldErrorMessage> details;

    // Constructor para errores de negocio normales
    public ErrorResponse(int status, String code, String message, String path) {
        this (
                status,
                code,
                message,
                path,
                null
        );
    }

    // Constructor completo (usado para validaciones)
    public ErrorResponse(
            int status,
            String code,
            String message,
            String path,
            List<FieldErrorMessage> details
    ) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        this.details = details;
    }

    @Getter
    @AllArgsConstructor
    public static class FieldErrorMessage {
        private String field;
        private String message;
    }

}