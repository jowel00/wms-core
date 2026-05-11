package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.InventoryEventService;
import com.wms.core.infrastructure.web.dto.response.InventoryEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory/events")
@RequiredArgsConstructor
public class InventoryEventController {

    private final InventoryEventService eventService;

    @GetMapping
    public List<InventoryEventResponse> getByContainer(@RequestParam UUID containerId) {
        return eventService.getEventsByContainer(containerId);
    }

}
