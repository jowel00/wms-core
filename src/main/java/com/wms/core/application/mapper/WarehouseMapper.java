package com.wms.core.application.mapper;

import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.web.dto.request.CreateWarehouseRequest;
import com.wms.core.infrastructure.web.dto.response.WarehouseResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WarehouseMapper {

    public Warehouse toDomain(CreateWarehouseRequest request, Owner owner){
        return new Warehouse(
                UUID.randomUUID(),
                owner,
                request.getName(),
                request.getCountryCode(),
                request.getCity(),
                "ACTIVE",
                null
        );
    }

    public WarehouseResponse toResponse(Warehouse warehouse){
        return new WarehouseResponse(
                warehouse.getWarehouseId(),
                warehouse.getOwner().getOwnerId(),
                warehouse.getName(),
                warehouse.getCountryCode(),
                warehouse.getCity()
        );
    }

    //Lista
    public List<WarehouseResponse> toResponseList(List<Warehouse> warehouses){
        return warehouses.stream().map(this::toResponse).toList();
    }
}
