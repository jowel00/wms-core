package com.wms.core.infrastructure.web.controller;

import com.wms.core.domain.service.LocationService;
import com.wms.core.infrastructure.web.dto.request.CreateLocationRequest;
import com.wms.core.infrastructure.web.dto.response.LocationResponse;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.generic.RET;
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
    public LocationResponse create( @Valid @RequestBody CreateLocationRequest request)
    {
        return locationService.createLocation(
                request.getWarehouseId(),
                request.getType(),
                request.getCode(),
                request.getParentLocationId()
        );

    }

    @GetMapping
    public List<LocationResponse> listByWarehouse(@RequestParam UUID warehouseId) {
        return locationService.listLocationsByWarehouse(warehouseId);
    }


}
