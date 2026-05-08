package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class SearchProductRequest {

    @NotNull(message = "ownerId es requerido")
    private UUID ownerId;

    private String search;

    @Min(value = 0, message = "La pagina debe ser 0 o mayor")
    private Integer page = 0;

    @Positive(message = "El tamaño debe ser mayor que 0")
    private Integer size = 10;

}
