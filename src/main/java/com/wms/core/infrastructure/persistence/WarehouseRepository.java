package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    List<Warehouse> findByOwner_OwnerId(UUID ownerId);

}
