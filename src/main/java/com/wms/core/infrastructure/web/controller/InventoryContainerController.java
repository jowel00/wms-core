package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.InventoryContainerService;
import com.wms.core.domain.exception.BusinessRuleException;
import com.wms.core.infrastructure.web.dto.request.CreateInventoryContainerRequest;
import com.wms.core.infrastructure.web.dto.response.ContainerDetailResponse;
import com.wms.core.infrastructure.web.dto.response.InventoryContainerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory/containers")
@RequiredArgsConstructor
public class InventoryContainerController {

    private final InventoryContainerService containerService;

    @PostMapping()
    public ResponseEntity<InventoryContainerResponse> create(@Valid @RequestBody CreateInventoryContainerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(containerService.createContainer(request));
    }

    @GetMapping("/{containerId}")
    public ContainerDetailResponse getById(@PathVariable UUID containerId) {
        return containerService.getContainerDetail(containerId);
    }

    @GetMapping
    public List<InventoryContainerResponse> getContainers(
            @RequestParam(required = false) UUID ownerId,
            @RequestParam(required = false) UUID warehouseId,
            @RequestParam(required = false) UUID locationId,
            @RequestParam(required = false) String status) {

        if (locationId != null) {
            return containerService.getContainersByLocation(locationId);
        }
        if (warehouseId != null) {
            return containerService.getContainersByWarehouse(warehouseId, status);
        }
        if (ownerId != null) {
            return containerService.getContainersByOwner(ownerId, status);
        }

        throw new BusinessRuleException(
                "MISSING_FILTER",
                "Debe proporcionar al menos un filtro: ownerId, warehouseId o locationId"
        );
    }

}
