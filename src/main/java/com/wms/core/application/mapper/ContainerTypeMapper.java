package com.wms.core.application.mapper;

import com.wms.core.domain.inventory.ContainerType;
import com.wms.core.infrastructure.web.dto.request.CreateContainerTypeRequest;
import com.wms.core.infrastructure.web.dto.response.ContainerTypeResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ContainerTypeMapper {

    public ContainerType toDomain(CreateContainerTypeRequest request) {
        return new ContainerType(
                UUID.randomUUID(),
                request.getName().trim().toUpperCase(),
                null
        );
    }

    public ContainerTypeResponse toResponse(ContainerType containerType) {
        return new ContainerTypeResponse(
                containerType.getTypeId(),
                containerType.getName()
        );
    }

    //Lista
    public List<ContainerTypeResponse> toResponseList(List<ContainerType> containerTypes) {
        return containerTypes.stream().map(this::toResponse).toList();
    }

}
