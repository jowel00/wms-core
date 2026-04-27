-- =========================
-- DOMAIN: CATALOG -> PRODUCTS
-- =========================
CREATE TABLE products (
  product_id UUID PRIMARY KEY,
  owner_id UUID NOT NULL,
  seller_sku VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  barcode_upc_ean VARCHAR(255),
  requires_unit_tracking BOOLEAN NOT NULL DEFAULT FALSE,
  has_expiration BOOLEAN NOT NULL DEFAULT FALSE,
  status VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),

  -- Todo producto pertenece a un cliente (Owner)
  CONSTRAINT fk_product_owner
    FOREIGN KEY (owner_id) REFERENCES owners(owner_id),
    
  -- REGLA DE NEGOCIO: Un SKU no se puede repetir para el mismo cliente
  CONSTRAINT uq_owner_sku 
    UNIQUE(owner_id, seller_sku)
);

-- ÍNDICE DE PERFORMANCE: Vital para el escáner y la carga masiva de 20k referencias
CREATE INDEX idx_products_owner_sku ON products(owner_id, seller_sku);