package com.wms.core.application.service;

import com.wms.core.application.mapper.LocationMapper;
import com.wms.core.domain.exception.BusinessRuleException;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.warehouse.LocationType;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.domain.warehouse.Location;
import com.wms.core.infrastructure.persistence.LocationRepository;
import com.wms.core.infrastructure.persistence.LocationTypeRepository;
import com.wms.core.infrastructure.persistence.WarehouseRepository;
import com.wms.core.infrastructure.web.dto.request.CreateLocationRequest;
import com.wms.core.infrastructure.web.dto.response.LocationResponse;
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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse", request.getWarehouseId()
                        ));

        LocationType type = locationTypeRepository.findById(request.getTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location type", request.getTypeId()
                        ));

        String code = generateLocationCode(warehouse.getWarehouseId(), type);

        Location parent = null;

        if (request.getParentLocationId() != null) {
            parent = locationRepository.findById(request.getParentLocationId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Parent location", request.getParentLocationId()
                            ));

            if (!parent.getWarehouse().getWarehouseId().equals(request.getWarehouseId())) {
                throw new BusinessRuleException(
                        "LOCATION_WAREHOUSE_MISMATCH",
                        "Parent location no pertenece al mismo Warehouse");
            }

            if (!parent.isActive()) {
                parent.activate();
                locationRepository.save(parent);
            }

        }

        Location location = locationMapper.toDomain(warehouse, type, code, parent);
        return locationMapper.toResponse(locationRepository.save(location));
    }

    public List<LocationResponse> listLocationsByWarehouse(UUID warehouseId) {

        if (!warehouseRepository.existsById(warehouseId)){
            throw new ResourceNotFoundException("Warehouse", warehouseId);
        }
        return locationMapper.toResponseList(
                locationRepository.findByWarehouse_WarehouseId(warehouseId)
        );
    }

    public void deactivateLocation(UUID locationId) {

        Location location = locationRepository.findById(locationId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Location", locationId)
                );

        if(!location.isActive()){
            return;
        }

        location.deactivate();
        locationRepository.save(location);
    }

    private String generateLocationCode(UUID warehouseId, LocationType type) {

        List<Location> locations = locationRepository.findByWarehouse_WarehouseIdAndLocationType_TypeId(
                warehouseId,
                type.getTypeId()
        );

        int next = locations.size() + 1;

        return type.getIndicator() + "-" + String.format("%03d",next);
    }

}
