package com.wms.core.application.mapper;

import com.wms.core.domain.inventory.ContainerLine;
import com.wms.core.domain.inventory.ContainerStatus;
import com.wms.core.domain.inventory.ContainerType;
import com.wms.core.domain.inventory.InventoryContainer;
import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Location;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.web.dto.response.ContainerDetailResponse;
import com.wms.core.infrastructure.web.dto.response.InventoryContainerResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class InventoryContainerMapper {

    public InventoryContainer toDomain(
            Owner owner,
            Warehouse warehouse,
            Location location,
            ContainerType type
    ) {
        return new InventoryContainer(
                UUID.randomUUID(),
                owner,
                warehouse,
                location,
                type,
                ContainerStatus.CREATED,
                null,
                null
        );
    }

    public ContainerDetailResponse toDetailResponse(InventoryContainer container, ContainerLine line) {
        return new ContainerDetailResponse(
                container.getContainerId(),
                line != null ? line.getProduct().getProductId() : null,
                line != null ? line.getQtyAvailable() : null,
                container.getLocation() != null ? container.getLocation().getCode() : null,
                container.getStatus().name()
        );
    }

    public InventoryContainerResponse toResponse(InventoryContainer container) {
        return new InventoryContainerResponse(
                container.getContainerId(),
                container.getOwner().getOwnerId(),
                container.getWarehouse().getWarehouseId(),
                container.getLocation() != null ? container.getLocation().getLocationId() : null,
                container.getContainerType().getName(),
                container.getStatus().name()
        );
    }

    public List<InventoryContainerResponse> toResponseList(List<InventoryContainer> containers) {
        return containers.stream().map(this::toResponse).toList();
    }

}
