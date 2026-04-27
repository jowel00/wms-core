package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class LotResponse {

    private final UUID lotId;
    private final UUID productId;
    private final UUID ownerId;
    private final UUID supplierId;
    private final String batchCode;
    private final LocalDate expiresAt;
    private final LocalDate receivedAt;

}
