package com.wms.core.infrastructure.web.dto.request.inventory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MoveContainerRequest {

    @NotNull(message = "toLocationId es requerido")
    private UUID toLocationId;

    private UUID containerId;
}
