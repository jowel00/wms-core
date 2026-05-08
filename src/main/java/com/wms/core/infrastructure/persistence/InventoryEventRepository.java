package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.Audit.InventoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryEventRepository extends JpaRepository<InventoryEvent, UUID> {

}
