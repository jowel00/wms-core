package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateInventoryContainerRequest {

    @NotNull(message = "owner_id is required")
    private UUID ownerId;

    @NotNull(message = "warehouse_id is required")
    private UUID warehouseId;

    @NotNull(message = "location_id is required")
    private UUID locationId;

    @NotBlank(message = "type is required")
    private String type;
}
