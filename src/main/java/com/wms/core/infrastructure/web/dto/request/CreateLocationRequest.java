package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateLocationRequest {

    @NotNull(message = "warehouseId es requerido")
    private UUID warehouseId;

    @NotNull(message = "typeId es requerido")
    private UUID typeId;

    private UUID parentLocationId;

}
