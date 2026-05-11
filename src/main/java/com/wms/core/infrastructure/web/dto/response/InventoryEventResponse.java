package com.wms.core.infrastructure.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"eventId", "ownerId", "warehouseId", "eventType", "containerId", "containerlineId", "productId",
        "lotId", "fromLocationId", "toLocationId", "quantity", "reason", "actorId", "createdAt"
})
public class InventoryEventResponse {

    private final UUID eventId;
    private final UUID ownerId;
    private final UUID warehouseId;
    private final String eventType;
    private final UUID containerId;
    private final UUID containerlineId;
    private final UUID productId;
    private final UUID lotId;
    private final UUID fromLocationId;
    private final UUID toLocationId;
    private final Integer quantity;
    private final String reason;
    private final UUID actorId;
    private final Instant createdAt;

}
