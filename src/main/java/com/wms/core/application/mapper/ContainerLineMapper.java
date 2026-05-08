package com.wms.core.application.mapper;

import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.inventory.ContainerLine;
import com.wms.core.domain.inventory.InventoryContainer;
import com.wms.core.domain.catalog.Lot;
import com.wms.core.infrastructure.web.dto.request.CreateContainerLineRequest;
import com.wms.core.infrastructure.web.dto.response.ContainerLineResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ContainerLineMapper {

    public ContainerLine toDomain(
            CreateContainerLineRequest request,
            InventoryContainer container,
            Product product,
            Lot lot
    ){
        ContainerLine line = new ContainerLine(
                UUID.randomUUID(),
                container,
                product,
                lot,
                request.getQuantity(),
                request.getQuantity(), //qtyAvailable = qtyTotal al crear
                0,                     //qtyReserved siempre en 0 al crear
                null
        );
        line.validateQuantities();
        return line;
    }

    public ContainerLineResponse toResponse(ContainerLine line){
        return new ContainerLineResponse(
                line.getContainerLineId(),
                line.getContainer().getContainerId(),
                line.getProduct().getProductId(),
                line.getLot() != null ? line.getLot().getLotId() : null,
                line.getQtyTotal(),
                line.getQtyAvailable(),
                line.getQtyReserved()
        );
    }

    public List<ContainerLineResponse> toResponseList(List<ContainerLine> lines){
        return lines.stream().map(this::toResponse).toList();
    }

}
