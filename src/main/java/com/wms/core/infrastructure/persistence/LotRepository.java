package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.catalog.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface LotRepository extends JpaRepository<Lot, UUID> {

    Optional<Lot> findByProduct_ProductIdAndBatchCodeAndExpiresAt(UUID productId, String batchCode, LocalDate expiresAt);

}
