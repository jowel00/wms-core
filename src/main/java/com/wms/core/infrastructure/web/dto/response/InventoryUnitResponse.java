package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class InventoryUnitResponse {

    private final UUID unitId;
    private final UUID productId;
    private final UUID warehouseId;
    private final UUID containerId;
    private final UUID currentLocationId;
    private final String unit_barcode;
    private final String manufacturer_barcode;
    private final UUID lotId;
    private final String status;

}
