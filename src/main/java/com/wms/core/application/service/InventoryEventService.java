package com.wms.core.application.service;

import com.wms.core.application.mapper.InventoryEventMapper;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.infrastructure.persistence.InventoryContainerRepository;
import com.wms.core.infrastructure.persistence.InventoryEventRepository;
import com.wms.core.infrastructure.web.dto.response.InventoryEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryEventService {

    private final InventoryEventRepository eventRepository;
    private final InventoryContainerRepository containerRepository;
    private final InventoryEventMapper eventMapper;

    public List<InventoryEventResponse> getEventsByContainer(UUID containerId) {

        containerRepository.findById(containerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Container", containerId));

        return eventMapper.toResponseList(
                eventRepository.findByContainer_ContainerId(containerId)
        );
    }

}
