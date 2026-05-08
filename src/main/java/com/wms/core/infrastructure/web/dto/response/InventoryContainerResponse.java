package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class InventoryContainerResponse {

    private final UUID containerId;
    private final UUID ownerId;
    private final UUID warehouseId;
    private final UUID locationId;
    private final String type;
    private final String status;

}
