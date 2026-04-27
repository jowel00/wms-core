-- =========================
-- INVENTORY CONTAINERS
-- =========================
CREATE TABLE inventory_containers (
 container_id UUID PRIMARY KEY,
 owner_id UUID NOT NULL,
 warehouse_id UUID NOT NULL,
 location_id UUID NOT NULL,
 type VARCHAR (20) NOT NULL,
 status VARCHAR (50) NOT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT now(),
 closed_at TIMESTAMP NULL,

 --
 CONSTRAINT fk_container_owner
   FOREIGN KEY (owner_id) REFERENCES owners (owner_id),

 CONSTRAINT fk_container_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses (warehouse_id),

 CONSTRAINT fk_container_location
    FOREIGN KEY (location_id) REFERENCES locations (location_id),

 CONSTRAINT chk_container_status
    CHECK (status IN ('CREATED', 'ACTIVE', 'CLOSED', 'QUARANTINE'))
 );

 -- =========================
 -- LOTS
 -- =========================
CREATE TABLE lots (
 lot_id UUID PRIMARY KEY,
 product_id UUID NOT NULL,
 owner_id UUID NOT NULL,
 supplier_id UUID NULL,
 batch_code VARCHAR (255) NULL,
 expires_at DATE NULL,
 received_at DATE NULL,

 CONSTRAINT fk_lot_product
    FOREIGN KEY (product_id) REFERENCES products (product_id),

 CONSTRAINT fk_lot_owner
    FOREIGN KEY (owner_id) REFERENCES owners (owner_id)
 );

-- =========================
 -- CONTAINER LINES
 -- =========================
CREATE TABLE container_lines (
 container_line_id UUID PRIMARY KEY,
 container_id UUID NOT NULL,
 product_id UUID NOT NULL,
 lot_id UUID NULL,
 qty_total INTEGER NOT NULL DEFAULT 0,
 qty_available INTEGER NOT NULL DEFAULT 0,
 qty_reserved INTEGER NOT NULL DEFAULT 0,
 created_at TIMESTAMP NOT NULL DEFAULT now(),

 CONSTRAINT fk_container_line_container
    FOREIGN KEY (container_id) REFERENCES inventory_containers (container_id),

 CONSTRAINT fk_container_line_product
    FOREIGN KEY (product_id) REFERENCES products (product_id),

 CONSTRAINT chk_qty_positive
    CHECK (qty_total + qty_available >= 0 AND qty_reserved >= 0),

 CONSTRAINT chk_qty_balance
    CHECK (qty_available + qty_reserved <= qty_total),

 CONSTRAINT uq_container_product
    UNIQUE (container_id, product_id)
);