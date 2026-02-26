package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOwnerRequest {

    @NotBlank(message = "name is required")
    private String name;

}
