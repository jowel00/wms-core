package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateProductRequest {

    @NotNull(message = "owner_id is required")
    private UUID ownerId;

    @NotBlank(message = "seller_sku is required")
    private String sellerSku;

    @NotBlank(message = "name is required")
    private String name;

    private String barcodeUpcEan;

    @NotNull(message = "requires unit tracking is required")
    private boolean requiresUnitTracking;

    @NotNull(message = "has expiration is required")
    private boolean hasExpiration;


    public UUID getOwnerId(){ return ownerId; }

    public String getSellerSku(){
        return sellerSku;
    }

    public String getName(){
        return name;
    }

    public String getBarcodeUpcEan(){
        return barcodeUpcEan;
    }

    public boolean isRequiresUnitTracking(){
        return requiresUnitTracking;
    }

    public boolean isHasExpiration(){
        return hasExpiration;
    }

}
