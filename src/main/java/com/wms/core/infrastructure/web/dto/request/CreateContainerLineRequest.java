package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateContainerLineRequest {

    @NotNull(message = "productId es requerido")
    private UUID productId;

    //@NotNull(message = "lotId es requerido")
    private UUID lotId;

    @NotNull(message = "quantity es requerido")
    @Min(value = 1, message = "quantity debe ser mayor a 0")
    private Integer quantity;

    // @NotNull(message = "containerId es requerido") Se setea desde el controller PathVariable
    private UUID containerId;

}
