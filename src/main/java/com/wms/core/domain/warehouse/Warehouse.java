package com.wms.core.domain.warehouse;

import com.wms.core.domain.owner.Owner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.EntityListeners;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "warehouses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
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

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

}
