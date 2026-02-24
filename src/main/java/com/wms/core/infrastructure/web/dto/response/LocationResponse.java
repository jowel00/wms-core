package com.wms.core.infrastructure.web.dto.response;

import java.util.UUID;

public class LocationResponse {

    private UUID locationId;
    private UUID warehouseId;
    private UUID parentLocationId;
    private String type;
    private String code;
    private boolean active;

    public LocationResponse(
            UUID locationId,
            UUID warehouseId,
            UUID parentLocationId,
            String type,
            String code,
            boolean active
    ){
        this.locationId = locationId;
        this.warehouseId = warehouseId;
        this.parentLocationId = parentLocationId;
        this.type = type;
        this.code = code;
        this.active = active;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public UUID getParentLocationId() {
        return parentLocationId;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public boolean isActive() {
        return active;
    }
}
