package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ContainerLineResponse {

    private final UUID containerLineId;
    private final UUID containerId;
    private final UUID productId;
    private final UUID lotId;
    private final Integer qtyTotal;
    private final Integer qtyAvailable;
    private final Integer qtyReserved;
}
