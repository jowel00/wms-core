package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.LocationTypeService;
import com.wms.core.domain.warehouse.LocationType;
import com.wms.core.infrastructure.web.dto.request.CreateLocationTypeRequest;
import com.wms.core.infrastructure.web.dto.response.LocationTypeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations-types")
@RequiredArgsConstructor
public class LocationTypeController {

    private final LocationTypeService locationTypeService;

    @PostMapping
    public ResponseEntity<LocationType> create(@Valid @RequestBody CreateLocationTypeRequest request){
        LocationType created = locationTypeService.createLocationType(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<LocationType>> createBulk(@Valid @RequestBody List<CreateLocationTypeRequest> requests){
        List<LocationType> created = locationTypeService.createBulk(requests);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<LocationTypeResponse>> getAll(){
        return ResponseEntity.ok(locationTypeService.getAll());
    }
}
