package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LocationResponse {

    private UUID locationId;
    private UUID warehouseId;
    private UUID parentLocationId;
    private String type;
    private String code;
    private boolean active;

}
