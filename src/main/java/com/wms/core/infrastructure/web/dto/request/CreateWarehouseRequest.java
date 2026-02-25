package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateWarehouseRequest {

    @NotNull(message = "owner_id is required")
    private UUID ownerId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "country code is required")
    private String countryCode;

    @NotBlank(message =  "city is required")
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
