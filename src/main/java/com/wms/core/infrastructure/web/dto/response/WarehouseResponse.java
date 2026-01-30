package com.wms.core.infrastructure.web.dto.response;

import java.util.UUID;

public class WarehouseResponse {

    private UUID warehouseId;
    private UUID ownerId;
    private String name;
    private String countryCode;
    private String city;

    public WarehouseResponse(
            UUID warehouseId,
            UUID ownerId,
            String name,
            String countryCode,
            String city
    ){
        this.warehouseId = warehouseId;
        this.ownerId = ownerId;
        this.name = name;
        this.countryCode = countryCode;
        this.city = city;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCity() {
        return city;
    }
}
