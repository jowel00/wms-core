package com.wms.core.domain.service;

import com.wms.core.domain.owner.Owner;
import com.wms.core.domain.product.Product;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.persistence.ProductRepository;
import com.wms.core.infrastructure.web.dto.request.CreateProductRequest;
import com.wms.core.infrastructure.web.dto.request.SearchProductRequest;
import com.wms.core.infrastructure.web.dto.response.ProductListResponse;
import com.wms.core.infrastructure.web.dto.response.ProductResponse;
import com.wms.core.infrastructure.web.exception.OwnerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;

    public ProductService(
            ProductRepository productRepository,
            OwnerRepository ownerRepository
    ){
        this.productRepository = productRepository;
        this.ownerRepository = ownerRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request){
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(()->
                        new OwnerNotFoundException(request.getOwnerId())
                );

        if (productRepository
                .existsByOwner_OwnerIdAndSellerSku(
                        request.getOwnerId(),
                        request.getSellerSku()
                )){
            throw new IllegalArgumentException(
                    "SKU already exists for this owner: " + request.getSellerSku()
            );
        }

        Product product = new Product(
                UUID.randomUUID(),
                owner,
                request.getSellerSku(),
                request.getName(),
                request.getBarcodeUpcEan(),
                request.isRequiresUnitTracking(),
                request.isHasExpiration(),
                "ACTIVE"
        );

        Product saved = productRepository.save(product);

        return toResponse(saved);
    };

    public ProductResponse getProduct(UUID productId){

        Product product = productRepository.findById(productId)
                .orElseThrow(()
                        -> new IllegalArgumentException("Product not found")
                );

        return toResponse(product);
    }

    public Page<ProductListResponse> searchProducts(SearchProductRequest request) {

        UUID ownerId = request.getOwnerId();

        // 1) Validar owner existe
        ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(ownerId));

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
        return result.map(this::toListResponse);
    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getProductId(),
                product.getOwner().getOwnerId(),
                product.getSellerSku(),
                product.getName(),
                product.getBarcodeUpcEan(),
                product.isRequiresUnitTracking(),
                product.isHasExpiration(),
                product.getStatus(),
                product.getCreateAt()
        );
    }

    private ProductListResponse toListResponse(Product product) {
        return new ProductListResponse(
                product.getProductId(),
                product.getSellerSku(),
                product.getName(),
                product.getBarcodeUpcEan(),
                product.isRequiresUnitTracking(),
                product.isHasExpiration()
        );
    }


}
