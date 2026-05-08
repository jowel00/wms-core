package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateLocationTypeRequest {

    @NotBlank(message = "name es requerido")
    private String name;

    @NotBlank(message = "indicator es requerido")
    private String indicator;
}
