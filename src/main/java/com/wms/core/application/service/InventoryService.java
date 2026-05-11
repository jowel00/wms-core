package com.wms.core.application.service;

import com.wms.core.application.mapper.InventoryMapper;
import com.wms.core.domain.catalog.Lot;
import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.exception.BusinessRuleException;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.inventory.*;
import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.warehouse.Location;
import com.wms.core.domain.warehouse.Warehouse;
import com.wms.core.infrastructure.persistence.*;
import com.wms.core.infrastructure.web.dto.request.inventory.MoveContainerRequest;
import com.wms.core.infrastructure.web.dto.request.inventory.PutawayContainerRequest;
import com.wms.core.infrastructure.web.dto.request.inventory.ReceiveInventoryRequest;
import com.wms.core.infrastructure.web.dto.response.StockResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.MoveContainerResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.PutawayContainerResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.ReceiveInventoryResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final OwnerRepository ownerRepository;
    private final WarehouseRepository warehouseRepository;
    private final ContainerTypeRepository containerTypeRepository;
    private final ProductRepository productRepository;
    private final InventoryContainerRepository containerRepository;
    private final ContainerLineRepository containerLineRepository;
    private final LotRepository lotRepository;
    private final LocationRepository locationRepository;
    private final InventoryEventRepository eventRepository;
    private final InventoryMapper inventoryMapper;

    //Events
    @Transactional
    public ReceiveInventoryResponse receive(ReceiveInventoryRequest request) {

        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner", request.getOwnerId()));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse", request.getWarehouseId()));

        ContainerType containerType = containerTypeRepository.findById(request.getTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Container type", request.getTypeId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", request.getProductId()));

        if (!warehouse.getOwner().getOwnerId().equals(request.getOwnerId())) {
            throw new BusinessRuleException(
                    "WAREHOUSE_OWNER_MISMATCH",
                    "El Warehouse no pertenece al Owner indicado"
            );
        }

        if (product.isHasExpiration() && request.getLot() == null) {
            throw new BusinessRuleException(
                    "LOT_REQUIRED",
                    "El producto requiere lote porque tiene fecha de expiración"
            );
        }

        Lot lot = null;
        if (request.getLot() != null) {
            if (request.getLot().getLotId() != null) {
                //Caso 1: Lote existente
                lot = lotRepository.findById(request.getLot().getLotId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Lot", request.getLot().getLotId()));

                //Validar que el lote pertenece al mismo producto
                if (!lot.getProduct().getProductId().equals(request.getProductId())) {
                    throw new BusinessRuleException(
                            "LOT_PRODUCT_MISMTACH",
                            "El lote no pertenece al producto indicado"
                    );
                }
            } else if (request.getLot().getBatchCode() != null && request.getLot().getExpiresAt() != null) {
                //Caso 2: buscar lote o crear uno nuevo
                lot = lotRepository.findByProduct_ProductIdAndBatchCodeAndExpiresAt(
                        request.getProductId(),
                        request.getLot().getBatchCode(),
                        request.getLot().getExpiresAt()
                ).orElseGet(() ->
                        lotRepository.save(inventoryMapper.toDomainLot(request, product, owner))
                );
            } else {
                throw new BusinessRuleException(
                        "INVALID_LOT_DATA",
                        "Debe proporcionar lotId o batchCode + expiresAt"
                );
            }
        }

        InventoryContainer container = containerRepository.save(
                inventoryMapper.toDomainContainer(owner,warehouse,containerType));

        ContainerLine line = containerLineRepository.save(
                inventoryMapper.toDomainLine(request, container, product, lot));

        eventRepository.save(
                inventoryMapper.toDomainEventReceive(request, owner, warehouse, container, line, product,lot)
        );

        return inventoryMapper.toReceiveResponse(container);
    }

    @Transactional
    public PutawayContainerResponse putaway (PutawayContainerRequest request) {

        InventoryContainer container = containerRepository.findById(request.getContainerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Container", request.getContainerId()));

        if (container.getStatus() != ContainerStatus.CREATED){
            throw new BusinessRuleException(
                    "INVALID_CONTAINER_STATUS",
                    "El container debe estar en estado CREATED para hacer putaway"
            );
        }

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location", request.getLocationId()));

        if (!location.getWarehouse().getWarehouseId().equals(container.getWarehouse().getWarehouseId())) {
            throw new BusinessRuleException(
                    "LOCATION_WAREHOUSE_MISMATCH",
                    "La Location no pertenece al mismo Warehouse del container"
            );
        }

        if (!location.isActive()){
            throw new BusinessRuleException(
                    "LOCATION_INACTIVE",
                    "El location no esta activo"
            );
        }

        container.putaway(location);
        containerRepository.save(container);

        //Registrar evento
        eventRepository.save(inventoryMapper.toDomainEventPutaway(container, location));

        return inventoryMapper.toPutawayResponse(container);

    }

    @Transactional
    public MoveContainerResponse move(MoveContainerRequest request) {

        InventoryContainer container = containerRepository.findById(request.getContainerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Container", request.getContainerId()));

        if (container.getStatus() != ContainerStatus.ACTIVE){
            throw new BusinessRuleException(
                    "INVALID_CONTAINER_STATUS",
                    "El contenedor debe estar en estado ACTIVE para hacer MOVE"
            );

        }

        Location fromLocation = container.getLocation();

        Location toLocation = locationRepository.findById(request.getToLocationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location", request.getToLocationId()));

        if (!toLocation.getWarehouse().getWarehouseId().equals(container.getWarehouse().getWarehouseId())) {
            throw new BusinessRuleException(
                    "LOCATION_WAREHOUSE_MISMATCH",
                    "El location destino no pertenece al mismo Warehouse del container"
            );
        }

        if (fromLocation.getLocationId().equals(toLocation.getLocationId())) {
            throw new BusinessRuleException(
                    "SAME_LOCATION",
                    "El container ya es encuentra en esta ubicación"
            );
        }

        container.move(toLocation);
        containerRepository.save(container);

        eventRepository.save(inventoryMapper.toDomainEventMove(container, fromLocation, toLocation));

        return inventoryMapper.toMoveResponse(container, fromLocation);

    }

    //Gets
    public StockResponse getStock(UUID productId, UUID warehouseId) {

        productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", productId));

        warehouseRepository.findById(warehouseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse", warehouseId));

        Integer totalAvailable = containerLineRepository
                .sumQtyAvailableByProductAndWarehouse(productId, warehouseId);

        return inventoryMapper.toStockResponse(productId, warehouseId, totalAvailable);
    }

}
