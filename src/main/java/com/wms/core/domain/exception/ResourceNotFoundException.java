package com.wms.core.domain.exception;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String entity, String fieldName, Object value) {
        super(String.format("%s con %s [%s] no encontrado", entity, fieldName, value),
                entity.toUpperCase() + "_NOT_FOUND");
    }

    public ResourceNotFoundException(String entity, Object id) {
        this(entity, "ID", id);
    }

}
