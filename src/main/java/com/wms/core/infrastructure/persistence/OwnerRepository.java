package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {
}
