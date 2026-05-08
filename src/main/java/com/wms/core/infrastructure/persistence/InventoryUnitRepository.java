package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.Unit.InventoryUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryUnitRepository extends JpaRepository<InventoryUnit, UUID> {

}
