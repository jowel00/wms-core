package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.warehouse.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    List<Location> findByWarehouse_WarehouseId(UUID warehouseId);

    List<Location> findByWarehouse_WarehouseIdAndLocationType_TypeId(UUID warehouseId, UUID typeId);
}
