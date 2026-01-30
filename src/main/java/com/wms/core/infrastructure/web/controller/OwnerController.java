package com.wms.core.infrastructure.web.controller;

import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.service.OwnerService;
import com.wms.core.infrastructure.web.dto.request.CreateOwnerRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public Owner create(@Valid @RequestBody CreateOwnerRequest request) {
        return ownerService.createOwner(request.getName());
    }

    @GetMapping("/{id}")
    public Owner get(@PathVariable UUID id) {
        return ownerService.getOwner(id);
    }
}
