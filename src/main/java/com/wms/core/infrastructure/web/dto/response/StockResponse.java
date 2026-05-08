package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class StockResponse {

    private final UUID productId;
    private final UUID warehouseId;
    private  final  Integer totalAvailable;

}
