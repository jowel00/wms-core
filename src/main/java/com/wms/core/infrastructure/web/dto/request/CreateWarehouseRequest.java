package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateWarehouseRequest {

    @NotNull(message = "owner_id is required")
    private UUID ownerId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "country code is required")
    private String countryCode;

    @NotBlank(message =  "city is required")
    private String city;

}
