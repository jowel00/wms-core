package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.OwnerService;
import com.wms.core.infrastructure.web.dto.request.CreateOwnerRequest;
import com.wms.core.infrastructure.web.dto.response.OwnerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerResponse> create(@Valid @RequestBody CreateOwnerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ownerService.createOwner(request));
    }

    @GetMapping("/{ownerId}")
    public OwnerResponse getById(@PathVariable UUID ownerId) {
        return ownerService.getOwner(ownerId);
    }

    @GetMapping
    public List<OwnerResponse> getAll() {
        return ownerService.getAllOwners();
    }

}
