package com.wms.core.domain.service;

import com.wms.core.domain.catalog.Product;
import com.wms.core.infrastructure.persistence.ProductRepository;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductBulkUploadService {

    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;

    public ProductBulkUploadService(ProductRepository productRepository, OwnerRepository ownerRepository) {
        this.productRepository = productRepository;
        this.ownerRepository = ownerRepository;
    }

    // @Transactional es CRÍTICO: Si el producto 19,999 falla, no se guarda NADA. 
    // nos evita bases de datos a medio cargar.
    @Transactional
    public int uploadProducts(UUID ownerId, MultipartFile csvFile) {
        // 1. Validar que el Owner existe
        var owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner no encontrado"));

        // 2. Parsear el CSV (Aquí Samuel puede usar OpenCSV o Apache Commons CSV)
        List<ProductCsvDto> parsedRows = parseCsv(csvFile);

        // 3. Validar duplicados DENTRO del mismo archivo CSV para evitar que explote la BD
        Set<String> skusInFile = new HashSet<>();
        List<Product> productsToSave = new ArrayList<>();

        for (ProductCsvDto row : parsedRows) {
            if (!skusInFile.add(row.getSellerSku())) {
                throw new IllegalArgumentException("El archivo contiene SKUs duplicados: " + row.getSellerSku());
            }

            // 4. Mapear a la entidad Product
            Product newProduct = new Product(
                    UUID.randomUUID(),
                    owner,
                    row.getSellerSku(),
                    row.getName(),
                    row.getBarcode(),
                    false, // requires_unit_tracking
                    false, // has_expiration
                    "ACTIVE"
            );
            productsToSave.add(newProduct);
        }

        // 5. El guardado masivo (Hibernate lo dividirá en lotes de 1000 gracias al .yml)
        productRepository.saveAll(productsToSave);

        return productsToSave.size(); // Retorna la cantidad de productos creados
    }

    private List<ProductCsvDto> parseCsv(MultipartFile file) {
        // Samuel: Implementar la lectura del archivo y convertirlo a DTOs
        return new ArrayList<>(); 
    }
}