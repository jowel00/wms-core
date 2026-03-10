package com.wms.core.application.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.wms.core.domain.catalog.Product;
import com.wms.core.domain.owner.Owner;
import com.wms.core.infrastructure.imports.csv.ProductCsvDto;
import com.wms.core.infrastructure.persistence.ProductRepository;
import com.wms.core.infrastructure.persistence.OwnerRepository;
import com.wms.core.infrastructure.web.exception.CsvParseException;
import com.wms.core.infrastructure.web.exception.OwnerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductBulkUploadService {

    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;

    // @Transactional es CRÍTICO: Si el producto 19,999 falla, no se guarda NADA.
    // Nos evita bases de datos a medio cargar.
    @Transactional
    public int uploadProducts(UUID ownerId, MultipartFile csvFile) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(()->
                        new OwnerNotFoundException(ownerId)
                );
        /*
        var owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner no encontrado: " + ownerId));
        */

        List<ProductCsvDto> parsedRows = parseCsv(csvFile);

        List<String> incomingSkus = parsedRows.stream()
                .map(ProductCsvDto::getSellerSku)
                .toList();

        List<String> duplicatedSkus = productRepository.findExistingSkus(ownerId, incomingSkus);
        if (!duplicatedSkus.isEmpty()) {
            List<String> errors = duplicatedSkus.stream()
                    .map(sku -> "SKU '" + sku + "' ya existe en la base de datos para este owner")
                    .toList();
            throw new CsvParseException(errors);
        }

        List<Product> productsToSave = parsedRows.stream()
                .map(row -> new Product(
                        UUID.randomUUID(),
                        owner,
                        row.getSellerSku(),
                        row.getName(),
                        row.getBarcode(),
                        false,
                        false,
                        "ACTIVE"
                ))
                .toList();

        // Hibernate dividirá en lotes de 1000 gracias a jdbc.batch_size en el .yml
        productRepository.saveAll(productsToSave);

        return productsToSave.size();
    }

    private List<ProductCsvDto> parseCsv(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CsvParseException(List.of("El archivo está vacío"));
        }

        List<String> errors = new ArrayList<>();
        List<ProductCsvDto> rows = new ArrayList<>();
        // Rastrea qué fila introdujo cada SKU para poder informar duplicados con precisión
        Map<String, Integer> skuToRow = new LinkedHashMap<>();

        try (CSVReader csvReader = new CSVReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String[] header = csvReader.readNext();
            if (header == null || header.length == 0) {
                throw new CsvParseException(List.of("El archivo CSV está vacío o no tiene encabezados"));
            }

            // Construir mapa de índices por nombre de columna (case-insensitive)
            Map<String, Integer> headerMap = new HashMap<>();
            for (int i = 0; i < header.length; i++) {
                headerMap.put(header[i].trim().toLowerCase(), i);
            }

            // Validar que las columnas obligatorias existan
            List<String> missing = new ArrayList<>();
            for (String col : List.of("seller_sku", "name")) {
                if (!headerMap.containsKey(col)) missing.add(col);
            }
            if (!missing.isEmpty()) {
                throw new CsvParseException(
                        List.of("Columnas requeridas faltantes en el encabezado: " + String.join(", ", missing))
                );
            }

            int skuIdx = headerMap.get("seller_sku");
            int nameIdx = headerMap.get("name");
            int barcodeIdx = headerMap.getOrDefault("barcode", -1);

            String[] line;
            int rowNum = 2; // La fila 1 es el encabezado

            while ((line = csvReader.readNext()) != null) {
                boolean rowValid = true;

                if (line.length <= Math.max(skuIdx, nameIdx)) {
                    errors.add("Fila " + rowNum + ": número de columnas insuficiente");
                    rowNum++;
                    continue;
                }

                String sku = line[skuIdx].trim();
                String name = line[nameIdx].trim();
                String barcode = (barcodeIdx >= 0 && barcodeIdx < line.length)
                        ? line[barcodeIdx].trim()
                        : null;
                if (barcode != null && barcode.isBlank()) barcode = null;

                if (sku.isBlank()) {
                    errors.add("Fila " + rowNum + ": seller_sku es requerido");
                    rowValid = false;
                } else if (skuToRow.containsKey(sku)) {
                    errors.add("Fila " + rowNum + ": SKU duplicado '" + sku
                            + "' (primera aparición en fila " + skuToRow.get(sku) + ")");
                    rowValid = false;
                } else {
                    skuToRow.put(sku, rowNum);
                }

                if (name.isBlank()) {
                    errors.add("Fila " + rowNum + ": name es requerido");
                    rowValid = false;
                }

                if (rowValid) {
                    ProductCsvDto dto = new ProductCsvDto();
                    dto.setSellerSku(sku);
                    dto.setName(name);
                    dto.setBarcode(barcode);
                    rows.add(dto);
                }

                rowNum++;
            }

        } catch (CsvParseException e) {
            throw e;
        } catch (IOException | CsvValidationException e) {
            throw new CsvParseException(List.of("Error al leer el archivo: " + e.getMessage()));
        }

        if (!errors.isEmpty()) {
            throw new CsvParseException(errors);
        }

        if (rows.isEmpty()) {
            throw new CsvParseException(List.of("El archivo CSV no contiene filas de datos"));
        }

        return rows;
    }
}
