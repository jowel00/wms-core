package com.wms.core.application.service;

import com.wms.core.application.mapper.WarehouseMapper;
import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.persistence.WarehouseRepository;
import com.wms.core.infrastructure.web.dto.request.CreateWarehouseRequest;
import com.wms.core.infrastructure.web.dto.response.WarehouseResponse;
import com.wms.core.infrastructure.web.exception.OwnerNotFoundException;
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

    public WarehouseResponse createWarehouse(CreateWarehouseRequest request){
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new OwnerNotFoundException(request.getOwnerId())
                );

        List<Warehouse> activeWarehouse =
                warehouseRepository.findByOwner_OwnerIdAndStatus(request.getOwnerId(), "ACTIVE");

        if (activeWarehouse.size() >= 2){
            throw new IllegalArgumentException(
                    "Owner " + request.getOwnerId() + " already has 2 active warehouse");
        }

        Warehouse warehouse = new Warehouse(
                UUID.randomUUID(),
                owner,
                request.getName(),
                request.getCountryCode(),
                request.getCity(),
                "ACTIVE",
                null
        );

        Warehouse saved = warehouseRepository.save(warehouse);
        return warehouseMapper.toResponse(saved);
    }

    public List<WarehouseResponse> listWarehousesByOwner(UUID ownerId) {

        ownerRepository.findById(ownerId)
                .orElseThrow(()-> new OwnerNotFoundException(ownerId));

        return warehouseRepository.findByOwner_OwnerId(ownerId)
                .stream()
                .map(warehouseMapper::toResponse)
                .toList();
    }

}
