package com.wms.core.application.mapper;

import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.web.dto.request.CreateOwnerRequest;
import com.wms.core.infrastructure.web.dto.response.OwnerResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OwnerMapper {

    public Owner toDomain(CreateOwnerRequest request) {
        return new Owner(
                UUID.randomUUID(),
                request.getName(),
                "ACTIVE",
                null
        );
    }

    public OwnerResponse toResponse(Owner owner) {
        return new OwnerResponse(
                owner.getOwnerId(),
                owner.getName(),
                owner.getStatus()
        );
    }

    //Lista
    public List<OwnerResponse> toResponseList(List<Owner> owners) {
        return owners.stream().map(this::toResponse).toList();
    }

}
