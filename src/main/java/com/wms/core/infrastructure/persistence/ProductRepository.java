package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByOwner_OwnerIdAndSellerSku(UUID ownerId, String sellerSku);

    @Query("""
        SELECT p FROM Product p 
        WHERE p.owner.ownerId = :ownerId 
        AND (
            LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) 
            OR LOWER(p.sellerSku) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)

    Page<Product> searchByOwnerAndText(
            @Param("ownerId") UUID ownerId,
            @Param("search") String search,
            Pageable pageable
    );


}
