package com.wms.core.application.service;

import com.wms.core.application.mapper.ContainerTypeMapper;
import com.wms.core.domain.exception.ResourceConflictException;
import com.wms.core.domain.inventory.ContainerType;
import com.wms.core.infrastructure.persistence.ContainerTypeRepository;
import com.wms.core.infrastructure.web.dto.request.CreateContainerTypeRequest;
import com.wms.core.infrastructure.web.dto.response.ContainerTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContainerTypeService {

    private final ContainerTypeRepository containerTypeRepository;
    private final ContainerTypeMapper containerTypeMapper;

    public ContainerTypeResponse createContainerType(CreateContainerTypeRequest request) {

        String name = request.getName().trim().toUpperCase();

        if (containerTypeRepository.existsByName(name)) {
            throw new ResourceConflictException("Container_Type", "name", name);
        }

        ContainerType containerType = containerTypeMapper.toDomain(request);
        return containerTypeMapper.toResponse(containerTypeRepository.save(containerType));
    }

    public List<ContainerTypeResponse> getAllContainerTypes() {

        return containerTypeMapper.toResponseList(
                containerTypeRepository.findAll()
        );
    }

}
