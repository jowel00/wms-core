package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateWarehouseRequest {

    @NotNull(message = "ownerId es requerido")
    private UUID ownerId;

    @NotBlank(message = "name es requerido")
    private String name;

    @NotBlank(message = "countryCode es requerido")
    private String countryCode;

    @NotBlank(message =  "city es requerido")
    private String city;

}
