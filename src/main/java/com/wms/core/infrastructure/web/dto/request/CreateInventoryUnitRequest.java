package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateInventoryUnitRequest {

    @NotNull(message = "ownerId es requerido")
    private UUID ownerId;

    @NotNull(message = "productId es requerido")
    private UUID productId;

    @NotNull(message = "warehouseId es requerido")
    private UUID warehouseId;

    private UUID containerId;

    private UUID currentLocationId;

    private String unitBarcode;

    private String manufacturerBarcode;

    private UUID lotId;
}
