package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateProductRequest {

    @NotNull(message = "ownerId es requerido")
    private UUID ownerId;

    @NotBlank(message = "sellerSku es requerido")
    private String sellerSku;

    @NotBlank(message = "name es requerido")
    private String name;

    private String barcodeUpcEan;

    @NotNull(message = "requiresUnitTracking es requerido")
    private boolean requiresUnitTracking;

    @NotNull(message = "hasExpiration es requerido")
    private boolean hasExpiration;

}
