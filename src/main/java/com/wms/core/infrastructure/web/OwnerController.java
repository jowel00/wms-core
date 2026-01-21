package com.wms.core.infrastructure.web;

import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.service.OwnerService;
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
    public Owner create(@RequestParam String name) {
        return ownerService.createOwner(name);
    }

    @GetMapping("/{id}")
    public Owner get(@PathVariable UUID id) {
        return ownerService.getOwner(id);
    }
}
