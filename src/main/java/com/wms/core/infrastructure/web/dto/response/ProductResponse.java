package com.wms.core.infrastructure.web.dto.response;

import java.time.Instant;
import java.util.UUID;

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

    public ProductResponse(
            UUID productId,
            UUID ownerId,
            String sellerSku,
            String name,
            String barcodeUpdEan,
            boolean requiresUnitTracking,
            boolean hasExpiration,
            String status,
            Instant createdAt
    ){
        this.productId = productId;
        this.ownerId = ownerId;
        this.sellerSku = sellerSku;
        this.name = name;
        this.barcodeUpdEan = barcodeUpdEan;
        this.requiresUnitTracking = requiresUnitTracking;
        this.hasExpiration = hasExpiration;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getSellerSku() {
        return sellerSku;
    }

    public String getName() {
        return name;
    }

    public String getBarcodeUpdEan() {
        return barcodeUpdEan;
    }

    public boolean isRequiresUnitTracking() {
        return requiresUnitTracking;
    }

    public boolean isHasExpiration() {
        return hasExpiration;
    }

    public String getStatus() { return status; }

    public Instant getCreatedAt() {return createdAt; }
}
