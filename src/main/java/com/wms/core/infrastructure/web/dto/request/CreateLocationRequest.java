package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateLocationRequest {

    @NotNull(message = "warehouse_id is required")
    private UUID warehouseId;

    @NotBlank(message = "type is required")
    private String type;

    @NotBlank(message = "code is required")
    private String code;

    private UUID parentLocationId;

}
