package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateInventoryContainerRequest {

    @NotNull(message = "ownerId es requerido")
    private UUID ownerId;

    @NotNull(message = "warehouseId es requerido")
    private UUID warehouseId;

    @NotNull(message = "locationId es requerido")
    private UUID locationId;

    @NotNull(message = "typeId es requerido")
    private UUID typeId;
}
