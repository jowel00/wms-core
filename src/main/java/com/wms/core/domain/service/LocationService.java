package com.wms.core.domain.service;

import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.domain.warehouse.location.Location;
import com.wms.core.infrastructure.persistence.LocationRepository;
import com.wms.core.infrastructure.persistence.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


//esta es una prueba 02
@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;

    public LocationService(
            LocationRepository locationRepository,
            WarehouseRepository warehouseRepository
    ) {
        this.locationRepository = locationRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Location createLocation(
            UUID warehouseId,
            String type,
            String code,
            UUID parentLocationId
    ) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));

        Location parent = null;
        if (parentLocationId != null) {
            parent = locationRepository.findById(parentLocationId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent location not found"));
        }

        Location location = new Location(
                UUID.randomUUID(),
                warehouse,
                type,
                code,
                parent,
                true
        );

        return locationRepository.save(location);
    }

    public List<Location> listLocationsByWarehouse(UUID warehouseId) {
        return locationRepository.findByWarehouse_WarehouseId(warehouseId);
    }
}
