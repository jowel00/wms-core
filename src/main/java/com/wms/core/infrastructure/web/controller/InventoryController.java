package com.wms.core.infrastructure.web.controller;

import com.wms.core.infrastructure.web.dto.request.InventoryReceiveRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    @PostMapping("/receive")
    public void receive(
            @Valid @RequestBody InventoryReceiveRequest request
    ){
        return ;
    }
}
