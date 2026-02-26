package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private UUID productId;
    private UUID ownerId;
    private String sellerSku;
    private String name;
    private String barcodeUpdEan;
    private boolean requiresUnitTracking;
    private boolean hasExpiration;
    private String status;
    private Instant createdAt;

}
