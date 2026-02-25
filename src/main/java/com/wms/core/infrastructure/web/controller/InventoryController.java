package com.wms.core.infrastructure.web.controller;

import com.wms.core.infrastructure.web.dto.request.InventoryReceiveRequest;
import com.wms.core.infrastructure.web.dto.response.InventoryReceiveResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @PostMapping("/receive")
    public InventoryReceiveResponse receive(
            @Valid @RequestBody InventoryReceiveRequest request
    ){
        return ;
    }
}
