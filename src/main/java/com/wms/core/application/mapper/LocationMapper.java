package com.wms.core.application.mapper;

import com.wms.core.domain.warehouse.Location;
import com.wms.core.infrastructure.web.dto.response.LocationResponse;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationResponse toResponse(Location location){
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