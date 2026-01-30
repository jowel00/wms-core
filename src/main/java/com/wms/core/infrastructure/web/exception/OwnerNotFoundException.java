package com.wms.core.infrastructure.web.exception;

public class OwnerNotFoundException extends RuntimeException {

    public OwnerNotFoundException(String message){
        super(message);
    }
}
