package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateLotRequest {

    @NotNull(message = " productId es requerido")
    private UUID productId;

    @NotNull(message = " ownerId es requerido")
    private UUID ownerId;

    private UUID supplierId;

    private String batchCode;

    private LocalDate expiresAt;

    private LocalDate receivedAt;

}
