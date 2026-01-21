-- =========================
-- OWNERS
-- =========================
CREATE TABLE owners (
  owner_id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  status VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- =========================
-- WAREHOUSES
-- =========================
CREATE TABLE warehouses (
  warehouse_id UUID PRIMARY KEY,
  owner_id UUID NOT NULL,
  name VARCHAR(255) NOT NULL,
  country_code VARCHAR(10) NOT NULL,
  city VARCHAR(100) NOT NULL,
  status VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),

  CONSTRAINT fk_warehouse_owner
    FOREIGN KEY (owner_id) REFERENCES owners(owner_id)
);

-- =========================
-- LOCATIONS
-- =========================
CREATE TABLE locations (
  location_id UUID PRIMARY KEY,
  warehouse_id UUID NOT NULL,
  type VARCHAR(50) NOT NULL,
  code VARCHAR(100) NOT NULL,
  parent_location_id UUID,
  active BOOLEAN NOT NULL DEFAULT true,
  created_at TIMESTAMP NOT NULL DEFAULT now(),

  CONSTRAINT fk_location_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id),

  CONSTRAINT fk_location_parent
    FOREIGN KEY (parent_location_id) REFERENCES locations(location_id),

  CONSTRAINT uq_location_code
    UNIQUE (warehouse_id, code)
);
