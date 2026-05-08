package com.wms.core.application.mapper;

import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.catalog.Lot;
import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.web.dto.request.CreateLotRequest;
import com.wms.core.infrastructure.web.dto.response.LotResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LotMapper {

    public Lot toDomain(
            CreateLotRequest request,
            Product product,
            Owner owner
    ) {
        return new Lot(
                UUID.randomUUID(),
                product,
                owner,
                request.getSupplierId(),
                request.getBatchCode(),
                request.getExpiresAt(),
                null,
                null
        );
    }

    public LotResponse toResponse(Lot lot) {
        return new LotResponse(
                lot.getLotId(),
                lot.getProduct().getProductId(),
                lot.getOwner().getOwnerId(),
                lot.getSupplierId(),
                lot.getBatchCode(),
                lot.getExpiresAt(),
                lot.getReceivedAt()
        );
    }

    //Lista
    public List<LotResponse> toResponseList(List<Lot> lots) {
        return lots.stream().map(this::toResponse).toList();
    }

}
