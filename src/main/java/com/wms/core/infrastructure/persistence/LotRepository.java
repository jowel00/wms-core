package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.inventory.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LotRepository extends JpaRepository<Lot, UUID> {

}
