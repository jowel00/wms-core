package com.wms.core.application.mapper;

import com.wms.core.domain.catalog.Product;
import com.wms.core.infrastructure.web.dto.response.ProductListResponse;
import com.wms.core.infrastructure.web.dto.response.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product){
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

    public ProductListResponse toListResponse(Product product){
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
