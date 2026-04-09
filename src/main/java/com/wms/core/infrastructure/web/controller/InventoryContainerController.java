package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.InventoryContainerService;
import com.wms.core.infrastructure.web.dto.request.CreateInventoryContainerRequest;
import com.wms.core.infrastructure.web.dto.response.InventoryContainerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory-containers")
@RequiredArgsConstructor
public class InventoryContainerController {

    private final InventoryContainerService containerService;

    @PostMapping()
    public ResponseEntity<InventoryContainerResponse> create(@Valid @RequestBody CreateInventoryContainerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(containerService.createContainer(request));
    }

    @GetMapping("/{id}")
    public InventoryContainerResponse getById(@PathVariable UUID id){
        return containerService.getContainer(id);
    }

    @GetMapping
    public List<InventoryContainerResponse> getContainers(
            @RequestParam(required = false) UUID ownerId,
            @RequestParam(required = false) UUID warehouseId,
            @RequestParam(required = false) UUID locationId) {

        if (locationId != null) {
            return containerService.getContainersByLocation(locationId);
        }
        if (warehouseId != null) {
            return containerService.getContainersByWarehouse(warehouseId);
        }
        if (ownerId != null) {
            return containerService.getContainersByOwner(ownerId);
        }

        throw new IllegalArgumentException(
                "Debe proporcionar al menos un filtro: ownerId, warehouseId o locationId"
        );
    }
}
