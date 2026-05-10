package com.wms.core.application.service;

import com.wms.core.application.mapper.InventoryContainerMapper;
import com.wms.core.domain.exception.BusinessRuleException;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.inventory.ContainerLine;
import com.wms.core.domain.inventory.ContainerStatus;
import com.wms.core.domain.inventory.ContainerType;
import com.wms.core.domain.inventory.InventoryContainer;
import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Location;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.persistence.*;
import com.wms.core.infrastructure.web.dto.request.CreateInventoryContainerRequest;
import com.wms.core.infrastructure.web.dto.response.ContainerDetailResponse;
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
    private final ContainerTypeRepository containerTypeRepository;
    private final ContainerLineRepository containerLineRepository;

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

        ContainerType type = containerTypeRepository.findById(request.getTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Container type", request.getTypeId()));


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

        //Validar location activa
        if (!location.isActive()){
            throw new BusinessRuleException(
                    "LOCATION_INACTIVE",
                    "El location no esta activo"
            );
        }

        InventoryContainer container = containerMapper.toDomain(owner, warehouse, location, type);
        return containerMapper.toResponse(containerRepository.save(container));

    }

    public ContainerDetailResponse getContainerDetail(UUID containerId) {
        InventoryContainer container = containerRepository.findById(containerId)
                .orElseThrow(() -> new ResourceNotFoundException("Container", containerId));

        ContainerLine line = containerLineRepository
                .findFirstByContainer_ContainerId(containerId)
                .orElse(null);

        return containerMapper.toDetailResponse(container, line);
    }

    public List<InventoryContainerResponse> getContainersByOwner(UUID ownerId, String status) {
        ownerRepository.findById(ownerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner", ownerId));
        if (status != null) {
            ContainerStatus containerStatus = ContainerStatus.valueOf(status.toUpperCase());
            return containerMapper.toResponseList(
                    containerRepository.findByOwner_OwnerIdAndStatus(ownerId, containerStatus)
            );
        }

        return containerMapper.toResponseList(
                containerRepository.findByOwner_OwnerId(ownerId)
        );
    }

    public List<InventoryContainerResponse> getContainersByWarehouse(UUID warehouseId, String status) {
        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", warehouseId));

        if (status != null) {
            ContainerStatus containerStatus = ContainerStatus.valueOf(status.toUpperCase());
            return containerMapper.toResponseList(
                    containerRepository.findByWarehouse_WarehouseIdAndStatus(warehouseId, containerStatus)
            );
        }

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



}
