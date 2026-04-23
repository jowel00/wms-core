package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateLocationTypeRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "indicator is required")
    private String indicator;
}
