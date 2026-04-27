package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.WarehouseService;
import com.wms.core.infrastructure.web.dto.request.CreateWarehouseRequest;
import com.wms.core.infrastructure.web.dto.response.WarehouseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseResponse> create(@Valid @RequestBody CreateWarehouseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseService.createWarehouse(request));
    }

    @GetMapping
    public List<WarehouseResponse> getWarehouses(
            @RequestParam(required = false) UUID ownerId){
        if (ownerId != null){
            return warehouseService.listWarehousesByOwner(ownerId);
        }
        return warehouseService.getAllWarehouses();
    }

    /*
    @GetMapping
    public List<WarehouseResponse> getWarehousesByOwner(@RequestParam UUID ownerId) {
        return warehouseService.listWarehousesByOwner(ownerId);
    }
    */
}
