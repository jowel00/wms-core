package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class WarehouseResponse {

    private final UUID warehouseId;
    private final UUID ownerId;
    private final String name;
    private final String countryCode;
    private final String city;

}
