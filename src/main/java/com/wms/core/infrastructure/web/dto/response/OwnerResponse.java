package com.wms.core.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OwnerResponse {

    private final UUID ownerId;
    private final String name;
    private final String status;

}
