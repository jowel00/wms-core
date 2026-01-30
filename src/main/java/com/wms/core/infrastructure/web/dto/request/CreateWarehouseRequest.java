package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateWarehouseRequest {

    @NotNull(message = "El owner id es obligaroio")
    private UUID ownerId;

    @NotBlank(message = "El nombres es obligatorio")
    private String name;

    @NotBlank(message = "El país es obligatorio")
    private String countryCode;

    @NotBlank(message =  "La ciudad es obligatoria")
    private String city;

    public UUID getOwnerId(){
        return ownerId;
    }

    public String getName(){
        return name;
    }

    public String getCountryCode(){
        return countryCode;
    }

    public String getCity(){
        return city;
    }
}
