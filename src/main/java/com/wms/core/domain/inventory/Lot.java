package com.wms.core.domain.inventory;

import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.owner.Owner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "lots")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Lot {

    @Id
    @Column(name = "lot_id", nullable = false)
    private UUID lotId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "supplier_id")
    private UUID supplierId;

    @Column(name = "batch_code")
    private String batchCode;

    @Column(name = "expires_at")
    private LocalDate expiresAt;

    @Column(name = "received_at")
    private LocalDate receivedAt;
}
