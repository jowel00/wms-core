package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.catalog.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Aquí Hibernate hará la magia de las 20k referencias
}