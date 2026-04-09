package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLocationTypeRequest {

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "indicator is required")
    private String indicator;
}
