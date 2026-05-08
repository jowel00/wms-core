package com.wms.core.application.mapper;

import com.wms.core.domain.Audit.EventType;
import com.wms.core.domain.Audit.InventoryEvent;
import com.wms.core.domain.catalog.Lot;
import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.inventory.*;
import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Location;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.web.dto.request.inventory.ReceiveInventoryRequest;
import com.wms.core.infrastructure.web.dto.response.StockResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.MoveContainerResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.PutawayContainerResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.ReceiveInventoryResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InventoryMapper {

    public Lot toDomainLot(ReceiveInventoryRequest request, Product product, Owner owner)
    {
        return new Lot(
                UUID.randomUUID(),
                product,
                owner,
                null,
                request.getLot().getBatchCode(),
                request.getLot().getExpiresAt(),
                null,
                null
        );
    }

    public InventoryContainer toDomainContainer(
            Owner owner,
            Warehouse warehouse,
            ContainerType containerType
    ) {
        return new InventoryContainer(
                UUID.randomUUID(),
                owner,
                warehouse,
                null,
                containerType,
                ContainerStatus.CREATED,
                null,
                null
        );
    }

    public ContainerLine toDomainLine(
            ReceiveInventoryRequest request,
            InventoryContainer container,
            Product product,
            Lot lot
    ) {
        return new ContainerLine(
                UUID.randomUUID(),
                container,
                product,
                lot,
                request.getQuantity(),
                request.getQuantity(),
                0,
                null
        );
    }

    public InventoryEvent toDomainEventReceive(
            ReceiveInventoryRequest request,
            Owner owner,
            Warehouse warehouse,
            InventoryContainer container,
            ContainerLine line,
            Product product,
            Lot lot
    ) {
        return new InventoryEvent(
                UUID.randomUUID(),
                owner,
                warehouse,
                EventType.RECEIVED,
                container,
                line,
                product,
                lot,
                null,
                null,
                request.getQuantity(),
                null,
                null,
                null
        );
    }

    public InventoryEvent toDomainEventPutaway(
            InventoryContainer container,
            Location location
    ) {
        return new InventoryEvent(
                UUID.randomUUID(),
                container.getOwner(),
                container.getWarehouse(),
                EventType.PUTAWAY,
                container,
                null,
                null,
                null,
                null,
                location,
                null,
                null,
                null,
                null
        );
    }

    public InventoryEvent toDomainEventMove(
            InventoryContainer container,
            Location fromLocation,
            Location toLocation
    ) {
        return new InventoryEvent(
                UUID.randomUUID(),
                container.getOwner(),
                container.getWarehouse(),
                EventType.MOVE,
                container,
                null,
                null,
                null,
                fromLocation,
                toLocation,
                null,
                null,
                null,
                null
        );
    }

    //RESPONSES

    public ReceiveInventoryResponse toReceiveResponse( InventoryContainer container) {
        return new ReceiveInventoryResponse (
                container.getContainerId(),
                container.getStatus().name()
        );
    }

    public PutawayContainerResponse toPutawayResponse(InventoryContainer container) {
        return new PutawayContainerResponse (
                container.getContainerId(),
                container.getStatus().name(),
                container.getLocation().getLocationId()
        );
    }

    public MoveContainerResponse toMoveResponse(InventoryContainer container, Location fromLocation) {

        return new MoveContainerResponse(
                container.getContainerId(),
                container.getStatus().name(),
                fromLocation.getLocationId(),
                container.getLocation().getLocationId()
        );
    }

    public StockResponse toStockResponse(UUID prooductId, UUID warehouse, Integer totalAvailable) {
        return new StockResponse(prooductId, warehouse, totalAvailable);
    }
}
