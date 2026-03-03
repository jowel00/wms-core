package com.wms.core.infrastructure.web.controller;

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
public class ProductController {

    private final ProductBulkUploadService bulkUploadService;

    public ProductController(ProductBulkUploadService bulkUploadService) {
        this.bulkUploadService = bulkUploadService;
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
}
