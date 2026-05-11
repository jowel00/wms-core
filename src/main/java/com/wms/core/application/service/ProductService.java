package com.wms.core.application.service;

import com.wms.core.application.mapper.ProductMapper;
import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.exception.BusinessRuleException;
import com.wms.core.domain.exception.ResourceNotFoundException;
import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.persistence.ProductRepository;
import com.wms.core.infrastructure.web.dto.request.CreateProductRequest;
import com.wms.core.infrastructure.web.dto.request.SearchProductRequest;
import com.wms.core.infrastructure.web.dto.response.ProductListResponse;
import com.wms.core.infrastructure.web.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(CreateProductRequest request) {

        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Owner", request.getOwnerId())
                );

        if (productRepository.existsByOwner_OwnerIdAndSellerSku(
                        request.getOwnerId(),
                        request.getSellerSku()
                )) {
            throw new BusinessRuleException(
                    "SKU_ALREADY_EXISTS_FOR_OWNER",
                    "El SKU [%s] ya esta registrado para este Owner",
                    request.getSellerSku()
            );
        }

        Product product = productMapper.toDomain(request, owner);
        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductResponse getProduct(String sku) {

        Product product = productRepository.findBySellerSku(sku)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Product","SKU", sku)
                );

        return productMapper.toResponse(product);
    }

    public Page<ProductListResponse> searchProducts(SearchProductRequest request) {

        UUID ownerId = request.getOwnerId();

        // 1) Validar owner existe
        ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner", ownerId));

        // 2) Crear PageRequest (controlado)
        int finalSize = Math.min(request.getSize(), 50);
        if (finalSize < 1){
            finalSize = 10;
        }

        Pageable pageable = PageRequest.of(request.getPage(), finalSize);

        // 3) Normalizar search para LIKE (IMPORTANTE para filtro)
        String searchTerm =
                (request.getSearch() == null || request.getSearch().isBlank())
                        ? ""      // si no viene search, filtro vacío = trae todo del owner
                        : request.getSearch().trim();

        // 4) Ejecutar query con LIKE controlado
        Page<Product> result =
                productRepository.searchByOwnerAndText(ownerId, searchTerm, pageable);

        // 5) Mapear a Response DTO
        return result.map(productMapper::toListResponse);
    }

}
