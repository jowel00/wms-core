package com.wms.core.application.service;

import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.web.dto.request.CreateOwnerRequest;
import com.wms.core.infrastructure.web.exception.OwnerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner createOwner(CreateOwnerRequest request) {
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
                .orElseThrow(() -> new OwnerNotFoundException(ownerId));
    }
}
