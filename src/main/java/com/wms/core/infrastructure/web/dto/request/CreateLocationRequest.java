package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateLocationRequest {

    @NotNull(message = "warehouse_id is required")
    private UUID warehouseId;

    @NotNull(message = "type_id is required")
    private UUID typeId;

    private UUID parentLocationId;

}
