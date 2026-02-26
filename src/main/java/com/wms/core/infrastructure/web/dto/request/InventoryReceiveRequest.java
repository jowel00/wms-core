package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class InventoryReceiveRequest {

    @NotNull(message = "")
    private UUID ownerId;

    @NotNull(message = "")
    private UUID warehouseId;

    @NotNull(message = "")
    private UUID locationId;

    @NotNull(message = "")
    private UUID productId;

    @NotNull(message = "")
    @Min(value = 1, message = "quantity must be greater than zero")
    private Integer quantity;

    @Size(max = 100, message = "lot must not exced 100 characters")
    private String lot;
}
