package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.audit.InventoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InventoryEventRepository extends JpaRepository<InventoryEvent, UUID> {

    List<InventoryEvent> findByContainer_ContainerId(UUID containerId);

}
