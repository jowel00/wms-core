package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class SearchProductRequest {

    @NotNull(message = "owner_id is required")
    private UUID ownerId;

    private String search;

    @Min(value = 0, message = "page must be zero or greater")
    private Integer page = 0;

    @Positive(message = "size must be greater than 0")
    private Integer size = 10;

}
