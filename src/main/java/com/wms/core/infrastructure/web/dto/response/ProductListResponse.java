package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductListResponse {

    private UUID productId;
    private String sellerSku;
    private String name;
    private String barcodeUpcEan;
    private boolean requiresUnitTracking;
    private boolean hasExpiration;

}
