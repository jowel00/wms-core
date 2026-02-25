package com.wms.core.domain.service;

import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.domain.warehouse.location.Location;
import com.wms.core.infrastructure.persistence.LocationRepository;
import com.wms.core.infrastructure.persistence.WarehouseRepository;
import com.wms.core.infrastructure.web.dto.request.CreateLocationRequest;
import com.wms.core.infrastructure.web.dto.response.LocationResponse;
import com.wms.core.infrastructure.web.exception.LocationNotFoundException;
import com.wms.core.infrastructure.web.exception.WarehouseNotFoundException;
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

    public LocationResponse createLocation(CreateLocationRequest request)
    {
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
            .orElseThrow(() -> new WarehouseNotFoundException(request.getWarehouseId()));

        if(locationRepository
                .existsByWarehouse_WarehouseIdAndCode(request.getWarehouseId(), request.getCode())){
            throw new IllegalArgumentException(
                    "Location code already exists in the warehouse"
            );
        }

        Location parent = null;

        if (request.getParentLocationId() != null) {
            parent = locationRepository.findById(request.getParentLocationId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Parent location not found")
                    );

            if (!parent.isActive()){
                throw new IllegalArgumentException(
                        "Parent location is inactive"
                );
            }

            if(!parent.getWarehouse().getWarehouseId().equals(request.getWarehouseId())){
                throw new IllegalArgumentException(
                        "Parent location does not belongs to the same warehouse");
            };

        }

        Location location = new Location(
                UUID.randomUUID(),
                warehouse,
                request.getType(),
                request.getCode(),
                parent,
                true
        );

        Location saved = locationRepository.save(location);

        return toResponse(saved);
    }

    public List<LocationResponse> listLocationsByWarehouse(UUID warehouseId) {
        return locationRepository.findByWarehouse_WarehouseId(warehouseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deactivateLocation(UUID locationId){

        Location location = locationRepository.findById(locationId)
                .orElseThrow(()->
                        new LocationNotFoundException(locationId)
                );

        if(!location.isActive()){
            return;
        }

        location.deactivate();
        locationRepository.save(location);
    }


    private LocationResponse toResponse(Location location){
        return new LocationResponse(
                location.getLocationId(),
                location.getWarehouse().getWarehouseId(),
                location.getParentLocation() != null
                    ? location.getParentLocation().getLocationId()
                        : null,
                location.getType(),
                location.getCode(),
                location.isActive()
        );
    }
}
