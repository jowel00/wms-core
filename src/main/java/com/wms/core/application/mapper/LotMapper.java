package com.wms.core.application.mapper;

import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.inventory.Lot;
import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.web.dto.response.LotResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class LotMapper {

    public Lot toDomain(
            Product product,
            Owner owner,
            UUID supplier,
            String batchCode,
            LocalDate expiresAt
    ){
        return new Lot(
                UUID.randomUUID(),
                product,
                owner,
                supplier,
                batchCode,
                expiresAt,
                null
        );
    }

    public LotResponse toResponse(Lot lot){
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
    public List<LotResponse> toResponseList(List<Lot> lots){
        return lots.stream().map(this::toResponse).toList();
    }

}
