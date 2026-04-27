package com.wms.core.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class CsvParseException extends RuntimeException {

    private final List<CsvRowError> errors;

    public CsvParseException(List<CsvRowError> errors) {
        super("El CSV contiene " + errors.size() + " error(es) de validación");
        this.errors = List.copyOf(errors);
    }

    public static CsvParseException of(String message){
        return new CsvParseException(
                List.of(new CsvRowError(null, null, message))
        );
    }

    @Getter
    @AllArgsConstructor
    public static class CsvRowError {
        private final Integer row;
        private final String field;
        private final String message;
    }

}
