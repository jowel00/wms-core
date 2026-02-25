package com.wms.core.domain.owner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

//Esto es una prueba para hacer mi primer commit

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Owner() {
    }

    public Owner(UUID ownerId, String name, String status) {
        this.ownerId = ownerId;
        this.name = name;
        this.status = status;
        this.createdAt = Instant.now();
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
