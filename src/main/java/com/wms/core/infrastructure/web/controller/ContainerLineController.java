package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.ContainerLineService;
import com.wms.core.infrastructure.web.dto.request.CreateContainerLineRequest;
import com.wms.core.infrastructure.web.dto.response.ContainerLineResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory-containers/{containerId}/lines")
@RequiredArgsConstructor
public class ContainerLineController {

    private final ContainerLineService containerLineService;

    @PostMapping()
    public ResponseEntity<ContainerLineResponse> received(
            @PathVariable UUID containerId,
            @Valid @RequestBody CreateContainerLineRequest request) {
        request.setContainerId(containerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(containerLineService.createContainerLine(request));
    }

    @GetMapping
    public List<ContainerLineResponse> getByContainer(@PathVariable UUID containerId) {
        return containerLineService.getLinesByContainer(containerId);
    }

    @GetMapping("/{lineId}")
    public ContainerLineResponse getById(
            @PathVariable UUID containerId,
            @PathVariable UUID lineId) {
        return containerLineService.getContainerLine(lineId);
    }


}
