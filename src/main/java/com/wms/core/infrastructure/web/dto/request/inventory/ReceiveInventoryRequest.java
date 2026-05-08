package com.wms.core.infrastructure.web.dto.request.inventory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReceiveInventoryRequest {

    @NotNull(message = "ownerId es requerido")
    private UUID ownerId;

    @NotNull(message = "warehouseId es requerido")
    private UUID warehouseId;

    @NotNull(message = "typeId es requerido")
    private UUID typeId;

    @NotNull(message = "productId es requerido")
    private UUID productId;

    @NotNull(message = "quantity es requerido")
    @Min(value = 1, message = "quantity debe ser mayor a 0")
    private Integer quantity;

    @Valid
    private LotRequest lot;

    @Data
    public static class LotRequest {
        //Caso 1: lote existente
        private UUID lotId;

        //Caso 2: lote nuevo
        private String batchCode;
        private LocalDate expiresAt;
        private LocalDate receivedAt;
        private UUID supplierId;
    }
}
