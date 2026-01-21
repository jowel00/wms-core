package com.wms.core.infrastructure.web;

import com.wms.core.domain.service.WarehouseService;
import com.wms.core.domain.warehouse.Warehouse;
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
    public Warehouse create(
            @RequestParam UUID ownerId,
            @RequestParam String name,
            @RequestParam String countryCode,
            @RequestParam String city
    ) {
        return warehouseService.createWarehouse(ownerId, name, countryCode, city);
    }

    @GetMapping
    public List<Warehouse> listByOwner(@RequestParam UUID ownerId) {
        return warehouseService.listWarehousesByOwner(ownerId);
    }
}
