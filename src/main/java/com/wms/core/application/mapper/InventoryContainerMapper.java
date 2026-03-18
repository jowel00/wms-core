package com.wms.core.application.mapper;

import com.wms.core.domain.inventory.ContainerStatus;
import com.wms.core.domain.inventory.InventoryContainer;
import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Location;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.web.dto.request.CreateInventoryContainerRequest;
import com.wms.core.infrastructure.web.dto.response.InventoryContainerResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class InventoryContainerMapper {

    public InventoryContainer toDomain(
            CreateInventoryContainerRequest request,
            Owner owner,
            Warehouse warehouse,
            Location location
    ){
        return new InventoryContainer(
                UUID.randomUUID(),
                owner,
                warehouse,
                location,
                request.getType().trim().toLowerCase(),
                ContainerStatus.CREATED,
                null,
                null
        );
    }

    public InventoryContainerResponse toResponse(InventoryContainer container){
        return new InventoryContainerResponse(
                container.getContainerId(),
                container.getOwner().getOwnerId(),
                container.getWarehouse().getWarehouseId(),
                container.getLocation().getLocationId(),
                container.getType(),
                container.getStatus().name()
        );
    }

    public List<InventoryContainerResponse> toResponseList(List<InventoryContainer> containers){
        return containers.stream().map(this::toResponse).toList();
    }
}
