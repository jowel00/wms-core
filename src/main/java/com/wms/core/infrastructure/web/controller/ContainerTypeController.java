package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.ContainerTypeService;
import com.wms.core.infrastructure.web.dto.request.CreateContainerTypeRequest;
import com.wms.core.infrastructure.web.dto.response.ContainerTypeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/container-types")
@RequiredArgsConstructor
public class ContainerTypeController {

    private final ContainerTypeService containerTypeService;

    @PostMapping
    public ResponseEntity<ContainerTypeResponse> create(@Valid @RequestBody CreateContainerTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(containerTypeService.createContainerType(request));
    }

    @GetMapping
    public List<ContainerTypeResponse> getAll() {
        return containerTypeService.getAllContainerTypes();
    }
}
