package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.LocationService;
import com.wms.core.infrastructure.web.dto.request.CreateLocationRequest;
import com.wms.core.infrastructure.web.dto.response.LocationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> create(@Valid @RequestBody CreateLocationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationService.createLocation(request));
    }

    @GetMapping
    public List<LocationResponse> listByWarehouse(@RequestParam UUID warehouseId) {
        return locationService.listLocationsByWarehouse(warehouseId);
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate (@PathVariable UUID id){
        locationService.deactivateLocation(id);
    }

}
