package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.LocationTypeService;
import com.wms.core.infrastructure.web.dto.request.CreateLocationTypeRequest;
import com.wms.core.infrastructure.web.dto.response.LocationTypeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location-types")
@RequiredArgsConstructor
public class LocationTypeController {

    private final LocationTypeService locationTypeService;

    @PostMapping
    public ResponseEntity<LocationTypeResponse> create(@Valid @RequestBody CreateLocationTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationTypeService.createLocationType(request));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<LocationTypeResponse>> createBulk(@Valid @RequestBody List<CreateLocationTypeRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationTypeService.createBulk(requests));
    }

    @GetMapping
    public List<LocationTypeResponse> getAll() {
        return locationTypeService.getAllLocationTypes();
    }
}
