package com.wms.core.infrastructure.web.exception;

import java.util.UUID;

public class OwnerNotFoundException extends RuntimeException {

    public OwnerNotFoundException(UUID ownerId){
        super("Owner with id: " + ownerId + " not found");
    }
}
