package com.wms.core.domain.warehouse.location;

import com.wms.core.domain.warehouse.Warehouse;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "locations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"warehouse_id", "code"})
        }
)
public class Location {

    @Id
    @Column(name = "location_id", nullable = false)
    private UUID locationId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_location_id")
    private Location parentLocation;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Location() {
    }

    public Location(
            UUID locationId,
            Warehouse warehouse,
            String type,
            String code,
            Location parentLocation,
            boolean active
    ) {
        this.locationId = locationId;
        this.warehouse = warehouse;
        this.type = type;
        this.code = code;
        this.parentLocation = parentLocation;
        this.active = active;
        this.createdAt = Instant.now();
    }

    public UUID getLocationId() {
        return locationId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void deactivate(){
        this.active = false ;
    }
}
