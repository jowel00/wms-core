package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.inventory.InventoryContainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InventoryContainerRepository extends JpaRepository<InventoryContainer, UUID> {

    List<InventoryContainer> findByOwner_OwnerId(UUID ownerId);

    List<InventoryContainer> findByWarehouse_WarehouseId(UUID warehouseId);

    List<InventoryContainer> findByLocation_LocationId(UUID locationId);

}
