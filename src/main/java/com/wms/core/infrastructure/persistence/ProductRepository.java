package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.catalog.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    // Aquí Hibernate hará la magia de las 20k referencias
    @Query("SELECT p.sellerSku FROM Product p WHERE p.owner.ownerId = :ownerId AND p.sellerSku IN :skus")
    List<String> findExistingSkus(@Param("ownerId") UUID ownerId, @Param("skus") List<String> skus);
}
