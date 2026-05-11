package com.wms.core.application.mapper;

import com.wms.core.domain.audit.InventoryEvent;
import com.wms.core.infrastructure.web.dto.response.InventoryEventResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryEventMapper {

    public InventoryEventResponse toResponse(InventoryEvent event) {
        return new InventoryEventResponse(
                event.getEventId(),
                event.getOwner().getOwnerId(),
                event.getWarehouse().getWarehouseId(),
                event.getEventType().name(),
                event.getContainer().getContainerId(),
                event.getLine() != null ? event.getLine().getContainerLineId() : null,
                event.getProduct() != null ? event.getProduct().getProductId() : null,
                event.getLot() != null ? event.getLot().getLotId() : null,
                event.getFromLocation() != null ? event.getFromLocation().getLocationId() : null,
                event.getToLocation() != null ? event.getToLocation().getLocationId() : null,
                event.getQuantity(),
                event.getReason(),
                event.getActorId(),
                event.getCreatedAt()
        );
    }

    public List<InventoryEventResponse> toResponseList(List<InventoryEvent> events) {
        return events.stream().map(this::toResponse).toList();
    }

}
