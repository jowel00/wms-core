package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.warehouse.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationTypeRepository extends JpaRepository<LocationType, UUID> {

    Optional<LocationType> findByName(String name);

    Optional<LocationType> findByIndicator(String indicator);

    List<LocationType> findByNameIn(List<String> names);

    List<LocationType> findByActiveTrue();
}
