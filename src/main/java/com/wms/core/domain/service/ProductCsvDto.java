package com.wms.core.domain.service;

import lombok.Data;

@Data
public class ProductCsvDto {
    private String sellerSku;
    private String name;
    private String barcode;
}