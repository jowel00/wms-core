package com.wms.core.infrastructure.imports.csv;

import lombok.Data;

@Data
public class ProductCsvDto {

    private String sellerSku;
    private String name;
    private String barcode;

}