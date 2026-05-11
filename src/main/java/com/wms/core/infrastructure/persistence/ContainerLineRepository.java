package com.wms.core.infrastructure.persistence;

import com.wms.core.domain.inventory.ContainerLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContainerLineRepository extends JpaRepository<ContainerLine, UUID> {

    boolean existsByContainer_ContainerIdAndProduct_ProductId(UUID containerId, UUID productId);

    List<ContainerLine> findByContainer_ContainerId(UUID containerId);

    // Container por containerId
    @Query("""
            SELECT cl FROM ContainerLine cl
            WHERE cl.container.containerId = :containerId
    """)
    Optional<ContainerLine> findFirstByContainer_ContainerId(@Param("containerId") UUID containerId);

    // Stock total disponible por producto y warehouse
    @Query("""
            SELECT COALESCE(SUM(cl.qtyAvailable), 0)
            FROM ContainerLine cl
            WHERE cl.product.productId = :productId
            AND cl.container.warehouse.warehouseId = :warehouseId
            AND cl.container.status = 'ACTIVE'
    """)
    Integer sumQtyAvailableByProductAndWarehouse(
            @Param("productId") UUID productId,
            @Param("warehouseId") UUID warehouseId
    );
}
