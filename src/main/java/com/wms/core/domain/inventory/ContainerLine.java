package com.wms.core.domain.inventory;

import com.wms.core.domain.catalog.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "container_lines")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContainerLine {

    @Id
    @Column(name = "container_line_id", nullable = false)
    private UUID containerLineId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "container_id")
    private InventoryContainer container;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "lot_id", nullable = true)
    private Lot lot;

    @Column(nullable = false)
    private Integer qtyTotal;

    @Column(nullable = false)
    private Integer qtyAvailable;

    @Column(nullable = false)
    private Integer qtyReserved;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
