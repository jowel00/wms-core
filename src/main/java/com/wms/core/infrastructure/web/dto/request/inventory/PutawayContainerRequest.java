package com.wms.core.infrastructure.web.dto.request.inventory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PutawayContainerRequest {

    @NotNull(message = "locationId es requerido")
    private UUID locationId;

    private UUID containerId;
}
