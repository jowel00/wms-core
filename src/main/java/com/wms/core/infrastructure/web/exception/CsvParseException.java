package com.wms.core.infrastructure.web.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CsvParseException extends RuntimeException {

    private final List<String> errors;

    public CsvParseException(List<String> errors) {
        super("El CSV contiene " + errors.size() + " error(es) de validación");
        this.errors = List.copyOf(errors);
    }

}
