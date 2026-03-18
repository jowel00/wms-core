package com.wms.core.domain.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class BaseException extends RuntimeException {

    private final String code;
    private final Map<String, String> errors;

    public BaseException(String message, String code){
        super(message);
        this.code = code;
        this.errors = null;
    }

    public BaseException(String message, String code, Map<String, String> errors){
        super(message);
        this.code = code;
        this.errors = errors;
    }

}
