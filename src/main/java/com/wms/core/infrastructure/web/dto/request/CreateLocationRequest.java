package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateLocationRequest {

    @NotNull(message = "warehouse_id is required")
    public UUID warehouseId;

    @NotBlank(message = "type is required")
    public String type;

    @NotBlank(message = "code is required")
    public String code;

    private UUID parentLocationId;

    public UUID getWarehouseId(){
        return warehouseId;
    }

    public String getType(){
        return type;
    }

    public String getCode(){
        return code;
    }

    public UUID getParentLocationId() {
        return parentLocationId;
    }
}
