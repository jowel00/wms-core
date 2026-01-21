package com.wms.core.domain.warehouse;

import com.wms.core.domain.owner.Owner;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "warehouses")
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

    protected Warehouse() {
    }

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

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCity() {
        return city;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
