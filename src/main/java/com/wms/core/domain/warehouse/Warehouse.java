package com.wms.core.domain.warehouse;

import com.wms.core.domain.owner.Owner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "warehouses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Warehouse {

    @Id
    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(nullable = false)
    private String name;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Warehouse(
            UUID warehouseId,
            Owner owner,
            String name,
            String countryCode,
            String city,
            String status
    ) {
        this.warehouseId = warehouseId;
        this.owner = owner;
        this.name = name;
        this.countryCode = countryCode;
        this.city = city;
        this.status = status;
        this.createdAt = Instant.now();
    }

}
