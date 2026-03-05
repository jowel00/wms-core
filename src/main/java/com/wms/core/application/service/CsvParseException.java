package com.wms.core.domain.service;

import java.util.List;

public class CsvParseException extends RuntimeException {

    private final List<String> errors;

    public CsvParseException(List<String> errors) {
        super("El CSV contiene " + errors.size() + " error(es) de validación");
        this.errors = List.copyOf(errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}
