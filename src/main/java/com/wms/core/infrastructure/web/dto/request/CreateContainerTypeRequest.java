package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateContainerTypeRequest {

    @NotBlank(message = "name es requerido")
    private String name;

}
