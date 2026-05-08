package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.inventory.ContainerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContainerTypeRepository extends JpaRepository<ContainerType, UUID> {

    boolean existsByName(String name);
}
