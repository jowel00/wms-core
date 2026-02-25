package com.wms.core.infrastructure.web.dto.response;

import java.util.UUID;

public class ProductListResponse {

    private UUID productId;
    private String sellerSku;
    private String name;
    private String barcodeUpcEan;
    private boolean requiresUnitTracking;
    private boolean hasExpiration;

    public ProductListResponse(
            UUID productId,
            String sellerSku,
            String name,
            String barcodeUpcEan,
            boolean requiresUnitTracking,
            boolean hasExpiration
    ) {
        this.productId = productId;
        this.sellerSku = sellerSku;
        this.name = name;
        this.barcodeUpcEan = barcodeUpcEan;
        this.requiresUnitTracking = requiresUnitTracking;
        this.hasExpiration = hasExpiration;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getSellerSku() {
        return sellerSku;
    }

    public String getName() {
        return name;
    }

    public String getBarcodeUpcEan() {
        return barcodeUpcEan;
    }

    public boolean isRequiresUnitTracking() {
        return requiresUnitTracking;
    }

    public boolean isHasExpiration() {
        return hasExpiration;
    }

}
