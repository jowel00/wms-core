package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.InventoryService;
import com.wms.core.infrastructure.web.dto.request.inventory.MoveContainerRequest;
import com.wms.core.infrastructure.web.dto.request.inventory.PutawayContainerRequest;
import com.wms.core.infrastructure.web.dto.request.inventory.ReceiveInventoryRequest;
import com.wms.core.infrastructure.web.dto.response.StockResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.MoveContainerResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.PutawayContainerResponse;
import com.wms.core.infrastructure.web.dto.response.inventory.ReceiveInventoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/receive")
    public ResponseEntity<ReceiveInventoryResponse> receive(
            @Valid @RequestBody ReceiveInventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.receive(request));
    }

    @PostMapping("/containers/{containerId}/putaway")
    public ResponseEntity<PutawayContainerResponse> putaway(
            @PathVariable UUID containerId,
            @Valid @RequestBody PutawayContainerRequest request) {
        request.setContainerId(containerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(inventoryService.putaway(request));
    }

    @PostMapping("/containers/{containerId}/move")
    public ResponseEntity<MoveContainerResponse> move(
            @PathVariable UUID containerId,
            @Valid @RequestBody MoveContainerRequest request) {
        request.setContainerId(containerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(inventoryService.move(request));
    }

    @GetMapping("/stock")
    public StockResponse getStock(
            @RequestParam UUID producId,
            @RequestParam UUID warehouseId
    ) {
        return inventoryService.getStock(producId, warehouseId);
    }

}
