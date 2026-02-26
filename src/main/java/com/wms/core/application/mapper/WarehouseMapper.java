package com.wms.core.application.mapper;

import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.web.dto.response.WarehouseResponse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    public WarehouseResponse toResponse(Warehouse warehouse){
        return new WarehouseResponse(
                warehouse.getWarehouseId(),
                warehouse.getOwner().getOwnerId(),
                warehouse.getName(),
                warehouse.getCountryCode(),
                warehouse.getCity()
        );
    }
}
