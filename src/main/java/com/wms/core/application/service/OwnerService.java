package com.wms.core.application.service;

import com.wms.core.application.mapper.OwnerMapper;
import com.wms.core.domain.exception.ResourceConflictException;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.web.dto.request.CreateOwnerRequest;
import com.wms.core.infrastructure.web.dto.response.OwnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerResponse createOwner(CreateOwnerRequest request) {

        if (ownerRepository.existsByName(request.getName())){
            throw new ResourceConflictException("Owner", "name", request.getName());
        }

        Owner owner = ownerMapper.toDomain(request);
        return ownerMapper.toResponse(ownerRepository.save(owner));
    }

    public List<OwnerResponse> getAllOwners() {
        return ownerMapper.toResponseList(ownerRepository.findAll());
    }

    public OwnerResponse getOwner(UUID ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner", ownerId));

        return ownerMapper.toResponse(owner);
    }

}
