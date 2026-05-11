package com.wms.core.application.service;

import com.wms.core.application.mapper.LotMapper;
import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.catalog.Lot;
import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.persistence.LotRepository;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.persistence.ProductRepository;
import com.wms.core.infrastructure.web.dto.request.CreateLotRequest;
import com.wms.core.infrastructure.web.dto.response.LotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LotService {

    private final LotRepository lotRepository;
    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;
    private final LotMapper lotMapper;

    public LotResponse createLot(CreateLotRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", request.getProductId()
                        ));

        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner", request.getOwnerId()
                        ));

        Lot lot = lotMapper.toDomain(request,product, owner);
        return lotMapper.toResponse(lotRepository.save(lot));

    }

    public List<LotResponse> getAllLots() {
        return lotMapper.toResponseList(lotRepository.findAll());
    }

    public LotResponse getLot(UUID lotId) {

        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new ResourceNotFoundException("Lot", lotId));

        return lotMapper.toResponse(lot);
    }

    public List<LotResponse> getLotsByProduct(UUID productId) {

        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produt", productId));

        return lotMapper.toResponseList(
                lotRepository.findByProduct_ProductId(productId)
        );

    }

}
