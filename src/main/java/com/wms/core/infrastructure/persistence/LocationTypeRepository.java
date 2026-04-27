package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.warehouse.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LocationTypeRepository extends JpaRepository<LocationType, UUID> {

    boolean existsByName(String name);

    boolean existsByIndicator(String indicator);

    List<LocationType> findByNameIn(List<String> names);

    List<LocationType> findByIndicatorIn(List<String> indicator);

    List<LocationType> findByActiveTrue();

}
