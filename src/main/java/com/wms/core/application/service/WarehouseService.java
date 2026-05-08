package com.wms.core.application.service;

import com.wms.core.application.mapper.WarehouseMapper;
import com.wms.core.domain.exception.BusinessRuleException;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.persistence.WarehouseRepository;
import com.wms.core.infrastructure.web.dto.request.CreateWarehouseRequest;
import com.wms.core.infrastructure.web.dto.response.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final OwnerRepository ownerRepository;
    private final WarehouseMapper warehouseMapper;

    public WarehouseResponse createWarehouse(CreateWarehouseRequest request) {

        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner", request.getOwnerId())
                );

        List<Warehouse> activeWarehouse =
                warehouseRepository.findByOwner_OwnerIdAndStatus(request.getOwnerId(), "ACTIVE");

        if (activeWarehouse.size() >= 2){
            throw new BusinessRuleException(
                    "MAX_ACTIVE_WAREHOUSES_REACHED",
                    "Owner con ID [%s] ya tiene [%s] warehouse activos",
                    request.getOwnerId(),2
            );
        }

        Warehouse warehouse = warehouseMapper.toDomain(request, owner);
        return warehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseMapper.toResponseList(warehouseRepository.findAll());
    }

    public List<WarehouseResponse> listWarehousesByOwner(UUID ownerId) {

        ownerRepository.findById(ownerId)
                .orElseThrow(()-> new ResourceNotFoundException("Owner", ownerId));

        return warehouseMapper.toResponseList(
                warehouseRepository.findByOwner_OwnerId(ownerId)
        );
    }

}
