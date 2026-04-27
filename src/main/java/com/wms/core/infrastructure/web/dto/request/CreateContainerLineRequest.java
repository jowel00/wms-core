package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateContainerLineRequest {

    @NotNull(message = "productId is required")
    private UUID productId;

    //@NotNull(message = "lotId is required")
    private UUID lotId;

    @NotNull(message = "qtyTotal is required")
    @Min(value = 1, message = "qtyTotal must be greater than 0")
    private Integer qtyTotal;

    // @NotNull(message = "containerId is required") Se setea desde el controller PathVariable
    private UUID containerId;

}
