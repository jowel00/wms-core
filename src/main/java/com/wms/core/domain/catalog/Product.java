package com.wms.core.domain.catalog;

import com.wms.core.domain.owner.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(nullable = false)
    private String sellerSku;

    @Column(nullable = false)
    private String name;

    private String barcodeUpcEan;
    private boolean requiresUnitTracking;
    private boolean hasExpiration;

    @Column(nullable = false)
    private String status;
}