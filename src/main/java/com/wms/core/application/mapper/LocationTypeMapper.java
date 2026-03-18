package com.wms.core.application.mapper;

import com.wms.core.domain.warehouse.LocationType;
import com.wms.core.infrastructure.web.dto.request.CreateLocationTypeRequest;
import com.wms.core.infrastructure.web.dto.response.LocationTypeResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LocationTypeMapper {

    public LocationType toDomain(CreateLocationTypeRequest request){
        return new LocationType(
                UUID.randomUUID(),
                request.getName().trim().toUpperCase(),
                request.getIndicator().trim().toUpperCase(),
                true,
                null
        );
    }

    public LocationTypeResponse toResponse(LocationType locationType){
        return new LocationTypeResponse(
                locationType.getTypeId(),
                locationType.getName(),
                locationType.getIndicator(),
                locationType.isActive()
        );
    }

    //Lista
    public List<LocationTypeResponse> toResponseList(List<LocationType> locationTypes){
        return locationTypes.stream().map(this::toResponse).toList();
    }
}
