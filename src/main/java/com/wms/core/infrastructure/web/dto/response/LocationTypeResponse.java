package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LocationTypeResponse {

    private UUID typeId;
    private String name;
    private String indicator;
    private Boolean isActive;
}
