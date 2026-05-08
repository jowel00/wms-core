package com.wms.core.application.service;

import com.wms.core.application.mapper.ContainerLineMapper;
import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.exception.ResourceConflictException;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.inventory.ContainerLine;
import com.wms.core.domain.inventory.ContainerStatus;
import com.wms.core.domain.inventory.InventoryContainer;
import com.wms.core.domain.catalog.Lot;
import com.wms.core.infrastructure.persistence.ContainerLineRepository;
import com.wms.core.infrastructure.persistence.InventoryContainerRepository;
import com.wms.core.infrastructure.persistence.LotRepository;
import com.wms.core.infrastructure.persistence.ProductRepository;
import com.wms.core.infrastructure.web.dto.request.CreateContainerLineRequest;
import com.wms.core.infrastructure.web.dto.response.ContainerLineResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContainerLineService {

    private final ContainerLineRepository containerLineRepository;
    private final InventoryContainerRepository inventoryContainerRepository;
    private final ProductRepository productRepository;
    private final LotRepository lotRepository;
    private final ContainerLineMapper containerLineMapper;
    private final InventoryContainerRepository containerRepository;

    @Transactional
    public ContainerLineResponse createContainerLine(CreateContainerLineRequest request) {

        InventoryContainer container = inventoryContainerRepository.findById(request.getContainerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Container", request.getContainerId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", request.getProductId()));

        // Lot opcional Fase 1
        Lot lot = null;
        if (request.getLotId() != null) {
            lot = lotRepository.findById(request.getLotId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Lot", request.getLotId()));
        }

        // 1 contenedor = 1 product_id
        if (containerLineRepository.existsByContainer_ContainerIdAndProduct_ProductId(
                request.getContainerId(), request.getProductId())) {
            throw new ResourceConflictException("ContainerLine", "productId", request.getProductId());
        }
        //Crear linea con qtyTotal del request
        ContainerLine line = containerLineMapper.toDomain(request, container, product, lot);

        // Activar el container si estaba en CREATED
        if (container.getStatus() == ContainerStatus.CREATED) {
        //    container.activate();
            containerRepository.save(container);
        }

        return containerLineMapper.toResponse(containerLineRepository.save(line));
    }

    public ContainerLineResponse getContainerLine(UUID id) {

        ContainerLine line = containerLineRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("ContainerLine", id));
        return containerLineMapper.toResponse(line);
    }

    public List<ContainerLineResponse> getLinesByContainer(UUID containerId){
        containerRepository.findById(containerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Container", containerId));

        return containerLineMapper.toResponseList(
                containerLineRepository.findByContainer_ContainerId(containerId)
        );
    }

}
