package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateLotRequest {

    @NotNull(message = " product_id is required")
    private UUID productId;

    @NotNull(message = " owner_id is required")
    private UUID ownerId;

    private UUID supplierId;

    private String batchCode;

    private LocalDate expiresAt;

    private LocalDate receivedAt; // ??
}
