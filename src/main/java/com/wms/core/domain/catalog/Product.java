package com.wms.core.domain.catalog;

import com.wms.core.domain.owner.Owner;
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
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"owner_id", "seller_sku"})
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "seller_sku", nullable = false)
    private String sellerSku;

    @Column(nullable = false)
    private String name;

    @Column(name = "barcode_upc_ean")
    private String barcodeUpcEan;

    @Column(name = "requires_unit_tracking", nullable = false)
    private boolean requiresUnitTracking = false;

    @Column(name = "has_expiration",nullable = false)
    private boolean hasExpiration = false;

    @Column(nullable = false)
    private String status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createAt;
}