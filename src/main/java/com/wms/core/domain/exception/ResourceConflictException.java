package com.wms.core.domain.exception;

import java.util.Map;

public class ResourceConflictException extends BaseException {

    public ResourceConflictException(String entity, String fieldName, Object value) {
        super(String.format("%s con %s [%s] ya existe", entity, fieldName, value),
                entity.toUpperCase() + "_ALREADY_EXISTS");
    }

    public ResourceConflictException(String resourceName, Map<String, String> conflicts) {
        super(
                "Existen múltilpes conflictos en " + resourceName,
                resourceName.toUpperCase() + "_CONFLICT",
                conflicts
        );
    }

}
