package com.wms.core.domain.catalog;

import com.wms.core.domain.owner.Owner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"owner_id", "seller_sku"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "created_at", nullable = false)
    private Instant createAt;

    public Product(
            UUID productId,
            Owner owner,
            String sellerSku,
            String name,
            String barcodeUpcEan,
            boolean requiresUnitTracking,
            boolean hasExpiration,
            String status
    ){
        this.productId = productId;
        this.owner = owner;
        this.sellerSku = sellerSku;
        this.name = name;
        this.barcodeUpcEan = barcodeUpcEan;
        this.requiresUnitTracking = requiresUnitTracking;
        this.hasExpiration = hasExpiration;
        this.status = status;
        this.createAt = Instant.now();
    }
}