package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ContainerDetailResponse {

    private final UUID containerId;
    private final UUID productId;
    private final Integer quantityAvailable;
    private final String location;
    private final String status;

}
