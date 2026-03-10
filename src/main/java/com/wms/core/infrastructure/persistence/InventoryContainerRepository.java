package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.inventory.InventoryContainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryContainerRepository extends JpaRepository<InventoryContainer, UUID> {

}
