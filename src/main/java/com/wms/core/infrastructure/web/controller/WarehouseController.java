package com.wms.core.infrastructure.web.controller;

import com.wms.core.domain.service.WarehouseService;
import com.wms.core.infrastructure.web.dto.request.CreateWarehouseRequest;
import com.wms.core.infrastructure.web.dto.response.WarehouseResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public WarehouseResponse create(@Valid @RequestBody CreateWarehouseRequest request) {
        return warehouseService.createWarehouse(request);
    }

    @GetMapping
    public List<WarehouseResponse> listByOwner(@RequestParam UUID ownerId) {
        return warehouseService.listWarehousesByOwner(ownerId);
    }
}
