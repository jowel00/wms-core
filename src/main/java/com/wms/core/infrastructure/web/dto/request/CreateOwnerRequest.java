package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateOwnerRequest {

    @NotBlank(message = "name is required")
    public String name;

    public String getName(){
        return name;
    }
}
