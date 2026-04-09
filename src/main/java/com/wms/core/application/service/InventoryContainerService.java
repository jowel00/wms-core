package com.wms.core.application.service;

import com.wms.core.application.mapper.InventoryContainerMapper;
import com.wms.core.domain.exception.BusinessRuleException;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.inventory.InventoryContainer;
import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Location;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.persistence.InventoryContainerRepository;
import com.wms.core.infrastructure.persistence.LocationRepository;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.persistence.WarehouseRepository;
import com.wms.core.infrastructure.web.dto.request.CreateInventoryContainerRequest;
import com.wms.core.infrastructure.web.dto.response.InventoryContainerResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryContainerService {

    private final InventoryContainerRepository containerRepository;
    private final OwnerRepository ownerRepository;
    private final WarehouseRepository warehouseRepository;
    private final LocationRepository locationRepository;
    private final InventoryContainerMapper containerMapper;

    @Transactional
    public InventoryContainerResponse createContainer(CreateInventoryContainerRequest request){

        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner", request.getOwnerId()));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse", request.getWarehouseId()));

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location", request.getLocationId()));

        if(!warehouse.getOwner().getOwnerId().equals(request.getOwnerId())){
            throw new BusinessRuleException(
                    "WAREHOUSE_OWNER_MISMATCH",
                    "El Warehouse no pertenece al owner indicado"
            );
        }

        if(!location.getWarehouse().getWarehouseId().equals(request.getWarehouseId())){
            throw new BusinessRuleException(
                    "LOCATION_WAREHOUSE_MISMATCH",
                    "El Location no pertenece al Warehouse indicado"
            );
        }

        List<String> validTypes = List.of("BOX", "TOTE", "PALLET");
        if (!validTypes.contains(request.getType().trim().toUpperCase())){
            throw new BusinessRuleException(
                    "INVALID_CONTAINER_TYPE",
                    "El tipo [%s] no es válido. Tipos permitidos: BOX, TOTE, PALLET",
                    request.getType()
            );
        }

        if (!location.isActive()){
            location.activate();
            locationRepository.save(location);
        }

        InventoryContainer container = containerMapper.toDomain(request,owner,warehouse,location);
        return containerMapper.toResponse(containerRepository.save(container));

    }

    public List<InventoryContainerResponse> getContainersByOwner(UUID ownerId){
        ownerRepository.findById(ownerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner", ownerId));

        return containerMapper.toResponseList(
                containerRepository.findByOwner_OwnerId(ownerId)
        );

    }

    public List<InventoryContainerResponse> getContainersByWarehouse(UUID warehouseId) {
        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", warehouseId));

        return containerMapper.toResponseList(
                containerRepository.findByWarehouse_WarehouseId(warehouseId)
        );
    }

    public List<InventoryContainerResponse> getContainersByLocation(UUID locationId) {
        locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location", locationId));

        return containerMapper.toResponseList(
                containerRepository.findByLocation_LocationId(locationId)
        );
    }

    public InventoryContainerResponse getContainer(UUID containerId) {
        InventoryContainer container = containerRepository.findById(containerId)
                .orElseThrow(() -> new ResourceNotFoundException("Container", containerId));

        return containerMapper.toResponse(container);
    }
}
