package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.LotService;
import com.wms.core.infrastructure.web.dto.request.CreateLotRequest;
import com.wms.core.infrastructure.web.dto.response.LotResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lots")
@RequiredArgsConstructor
public class LotController {

    private final LotService lotService;

    @PostMapping
    public ResponseEntity<LotResponse> create(@Valid @RequestBody CreateLotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lotService.createLot(request));
    }

    @GetMapping("/{lotId}")
    public LotResponse getById(@PathVariable UUID lotId) {
        return lotService.getLot(lotId);
    }

    @GetMapping
    public List<LotResponse> getLots() {
        return lotService.getAllLots();
    }

}
