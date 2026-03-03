package com.wms.core.domain.service;

import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.web.exception.OwnerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner createOwner(String name) {
        Owner owner = new Owner(
                UUID.randomUUID(),
                name,
                "ACTIVE",
                null
        );
        return ownerRepository.save(owner);
    }

    public Owner getOwner(UUID ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found id: " + ownerId));
    }
}
