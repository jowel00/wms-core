package com.wms.core.infrastructure.web.exception;

import java.util.UUID;

public class LocationNotFoundException extends RuntimeException{

    public LocationNotFoundException(UUID locationId){
        super("Location with id: " + locationId + " not found ");
    }
}
