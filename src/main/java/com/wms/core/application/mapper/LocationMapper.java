package com.wms.core.application.mapper;

import com.wms.core.domain.warehouse.Location;
import com.wms.core.domain.warehouse.LocationType;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.web.dto.response.LocationResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LocationMapper {

    public Location toDomain(
            Warehouse warehouse,
            LocationType type,
            String code,
            Location parent
    ){
        return new Location(
                UUID.randomUUID(),
                warehouse,
                type,
                code,
                parent,
                false,
                null
        );
    }

    public LocationResponse toResponse(Location location){
        return new LocationResponse(
                location.getLocationId(),
                location.getWarehouse().getWarehouseId(),
                location.getParentLocation() != null
                    ? location.getParentLocation().getLocationId()
                        : null,
                location.getLocationType().getName(),
                location.getCode(),
                location.isActive()
        );
    }

    //Lista
    public List<LocationResponse> toResponseList(List<Location> locations){
        return locations.stream().map(this::toResponse).toList();
    }
}