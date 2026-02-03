package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.warehouse.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    List<Location> findByWarehouse_WarehouseId(UUID warehouseId);

    Optional<Location> findByWarehouse_WarehouseIdAndCode(UUID warehouseId, String code);

    boolean existsByWarehouse_WarehouseIdAndCode(UUID warehouseId, String code);
}
