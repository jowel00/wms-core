package com.wms.core.infrastructure.web.controller;

import com.wms.core.domain.service.ProductService;
import com.wms.core.infrastructure.web.dto.request.CreateProductRequest;
import com.wms.core.infrastructure.web.dto.request.SearchProductRequest;
import com.wms.core.infrastructure.web.dto.response.ProductListResponse;
import com.wms.core.infrastructure.web.dto.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request){
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable UUID id){
        return productService.getProduct(id);
    }

    @GetMapping
    public Page<ProductListResponse> searchProducts(@Valid SearchProductRequest request){
        return productService.searchProducts(request);
    }
}
