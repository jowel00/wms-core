package com.wms.core.infrastructure.web.dto.response.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MoveContainerResponse {

    private final UUID containerId;
    private final String status;
    private final UUID fromLocationId;
    private final UUID toLocationId;

}
