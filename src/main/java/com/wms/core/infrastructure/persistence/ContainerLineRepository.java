package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.inventory.ContainerLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContainerLineRepository extends JpaRepository<ContainerLine, UUID> {

}
