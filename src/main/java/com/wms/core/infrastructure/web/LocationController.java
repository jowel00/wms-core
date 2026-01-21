package com.wms.core.infrastructure.web;

import com.wms.core.domain.service.LocationService;
import com.wms.core.domain.warehouse.location.Location;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public Location create(
            @RequestParam UUID warehouseId,
            @RequestParam String type,
            @RequestParam String code,
            @RequestParam(required = false) UUID parentLocationId
    ) {
        return locationService.createLocation(
                warehouseId, type, code, parentLocationId
        );
    }

    @GetMapping
    public List<Location> listByWarehouse(@RequestParam UUID warehouseId) {
        return locationService.listLocationsByWarehouse(warehouseId);
    }
}
