package com.wms.core.application.service;

import com.wms.core.application.mapper.LocationMapper;
import com.wms.core.domain.warehouse.LocationType;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.domain.warehouse.Location;
import com.wms.core.infrastructure.persistence.LocationRepository;
import com.wms.core.infrastructure.persistence.LocationTypeRepository;
import com.wms.core.infrastructure.persistence.WarehouseRepository;
import com.wms.core.infrastructure.web.dto.request.CreateLocationRequest;
import com.wms.core.infrastructure.web.dto.response.LocationResponse;
import com.wms.core.infrastructure.web.exception.LocationNotFoundException;
import com.wms.core.infrastructure.web.exception.WarehouseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;
    private final LocationMapper locationMapper;
    private final LocationTypeRepository locationTypeRepository;

    public LocationResponse createLocation(CreateLocationRequest request) {

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
            .orElseThrow(() -> new WarehouseNotFoundException(request.getWarehouseId()));

        LocationType type = locationTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Location type not found"
                ));

        String code = generateLocationCode(warehouse.getWarehouseId(),type);

        Location parent = null;

        if (request.getParentLocationId() != null) {
            parent = locationRepository.findById(request.getParentLocationId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Parent location not found")
                    );

            if (!parent.getWarehouse().getWarehouseId().equals(request.getWarehouseId())) {
                throw new IllegalArgumentException(
                        "Parent location does not belongs to the same warehouse");
            }

            if (!parent.isActive()){
                parent.setActive(true);
                locationRepository.save(parent);
            }

        }

        Location location = new Location(
                UUID.randomUUID(),
                warehouse,
                type,
                code,
                parent,
                false,
                null
        );

        Location saved = locationRepository.save(location);
        return locationMapper.toResponse(saved);
    }

    public List<LocationResponse> listLocationsByWarehouse(UUID warehouseId) {
        return locationRepository.findByWarehouse_WarehouseId(warehouseId)
                .stream()
                .map(locationMapper::toResponse)
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

        location.setActive(false);
        locationRepository.save(location);
    }


    private String generateLocationCode(UUID warehouseId, LocationType type){

        List<Location> locations = locationRepository.findByWarehouse_WarehouseIdAndLocationType_TypeId(
                warehouseId,
                type.getTypeId()
        );

        int next = locations.size() + 1;

        return type.getIndicator() + "-" + String.format("%03d",next);
    }

}
