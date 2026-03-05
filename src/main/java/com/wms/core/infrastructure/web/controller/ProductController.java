package com.wms.core.infrastructure.web.controller;

import com.wms.core.application.service.ProductService;
import com.wms.core.infrastructure.web.dto.request.CreateProductRequest;
import com.wms.core.infrastructure.web.dto.request.SearchProductRequest;
import com.wms.core.infrastructure.web.dto.response.ProductListResponse;
import com.wms.core.infrastructure.web.dto.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.wms.core.domain.service.ProductBulkUploadService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductBulkUploadService bulkUploadService;

    @PostMapping
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request){
        return productService.createProduct(request);
    }

    @PostMapping(value = "/bulk-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> bulkUpload(
            @RequestParam UUID ownerId,
            @RequestParam("file") MultipartFile file
    ) {
        int created = bulkUploadService.uploadProducts(ownerId, file);
        return ResponseEntity.ok(Map.of(
                "message", "Carga masiva completada exitosamente",
                "productsCreated", created
        ));
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
