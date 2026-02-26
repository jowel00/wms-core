package com.wms.core.infrastructure.web.controller;

import com.wms.core.domain.owner.Owner;
import com.wms.core.application.service.OwnerService;
import com.wms.core.infrastructure.web.dto.request.CreateOwnerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public Owner create(@Valid @RequestBody CreateOwnerRequest request) {
        return ownerService.createOwner(request);
    }

    @GetMapping("/{id}")
    public Owner get(@PathVariable UUID id) {
        return ownerService.getOwner(id);
    }
}
