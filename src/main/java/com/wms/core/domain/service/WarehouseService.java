package com.wms.core.domain.service;

import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.persistence.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final OwnerRepository ownerRepository;

    public WarehouseService(
            WarehouseRepository warehouseRepository,
            OwnerRepository ownerRepository
    ) {
        this.warehouseRepository = warehouseRepository;
        this.ownerRepository = ownerRepository;
    }

    public Warehouse createWarehouse(
            UUID ownerId,
            String name,
            String countryCode,
            String city
    ) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        Warehouse warehouse = new Warehouse(
                UUID.randomUUID(),
                owner,
                name,
                countryCode,
                city,
                "ACTIVE"
        );

        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> listWarehousesByOwner(UUID ownerId) {
        return warehouseRepository.findByOwner_OwnerId(ownerId);
    }
}
