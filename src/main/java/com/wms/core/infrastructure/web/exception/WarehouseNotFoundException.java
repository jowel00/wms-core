package com.wms.core.infrastructure.web.exception;

import java.util.UUID;

public class WarehouseNotFoundException extends RuntimeException{

    public WarehouseNotFoundException(UUID warehouseId){
        super("Warehouse with id: " + warehouseId + " not found");
    }
}
