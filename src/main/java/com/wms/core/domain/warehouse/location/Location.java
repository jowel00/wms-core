package com.wms.core.domain.warehouse.location;

import com.wms.core.domain.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "locations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"warehouse_id", "code"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Location(
            UUID locationId,
            Warehouse warehouse,
            String type,
            String code,
            Location parentLocation
    ) {
        this.locationId = locationId;
        this.warehouse = warehouse;
        this.type = type;
        this.code = code;
        this.parentLocation = parentLocation;
        this.active = true;
        this.createdAt = Instant.now();
    }

    public void deactivate(){
        this.active = false ;
    }
}
