package com.wms.core.application.mapper;

import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.imports.csv.ProductCsvDto;
import com.wms.core.infrastructure.web.dto.request.CreateProductRequest;
import com.wms.core.infrastructure.web.dto.response.ProductListResponse;
import com.wms.core.infrastructure.web.dto.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductMapper {

    public Product toDomain(CreateProductRequest request, Owner owner) {
        return new Product(
                UUID.randomUUID(),
                owner,
                request.getSellerSku(),
                request.getName(),
                request.getBarcodeUpcEan(),
                request.isRequiresUnitTracking(),
                request.isHasExpiration(),
                "ACTIVE",
                null
        );
    }

    public Product toDomain(ProductCsvDto row, Owner owner) {
        return new Product(
                UUID.randomUUID(),
                owner,
                row.getSellerSku(),
                row.getName(),
                row.getBarcode(),
                false,
                false,
                "ACTIVE",
                null
        );
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getOwner().getOwnerId(),
                product.getSellerSku(),
                product.getName(),
                product.getBarcodeUpcEan(),
                product.isRequiresUnitTracking(),
                product.isHasExpiration(),
                product.getStatus()
        );
    }

    public ProductListResponse toListResponse(Product product) {
        return new ProductListResponse(
                product.getProductId(),
                product.getSellerSku(),
                product.getName(),
                product.getBarcodeUpcEan(),
                product.isRequiresUnitTracking(),
                product.isHasExpiration()
        );
    }

    public List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream().map(this::toResponse).toList();
    }

    public List<ProductListResponse> toListResponseList(List<Product> products) {
        return products.stream().map(this::toListResponse).toList();
    }

}
