-- =========================
-- CONTAINER TYPES
-- =========================
CREATE TABLE container_types (
 type_id UUID PRIMARY KEY,
 name VARCHAR(50) NOT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT now(),

 CONSTRAINT uq_container_type_name UNIQUE (name)
);

-- =========================
-- SEED INITIAL TYPES
-- =========================
INSERT INTO container_types (type_id, name)
VALUES
(RANDOM_UUID(), 'BOX'),
(RANDOM_UUID(), 'TOTE'),
(RANDOM_UUID(), 'PALLET');

-- =========================
-- MODIFY INVENTORY CONTAINERS TABLE
-- =========================

-- 3. Iniciar la refactorización de inventory_containers
-- Paso A: Agregar la nueva columna permitiendo nulos (para no romper registros existentes)
ALTER TABLE inventory_containers
ADD COLUMN type_id UUID;

-- Paso C: Hacer la nueva columna obligatoria y agregar la llave foránea
ALTER TABLE inventory_containers
ALTER COLUMN type_id SET NOT NULL;

ALTER TABLE inventory_containers
ADD CONSTRAINT fk_container_type
FOREIGN KEY (type_id) REFERENCES container_types(type_id);

-- Paso D: Eliminar la columna de texto vieja, ya no la necesitamos
ALTER TABLE inventory_containers
DROP COLUMN type;

-- =========================================================================
-- 4. Crear el Libro Mayor (Ledger) de Eventos de Inventario
-- =========================================================================
CREATE TABLE inventory_events (
  event_id UUID PRIMARY KEY,
  owner_id UUID NOT NULL,
  warehouse_id UUID NOT NULL,
  event_type VARCHAR(50) NOT NULL,
  container_id UUID NULL,
  container_line_id UUID NULL,
  product_id UUID NULL,
  lot_id UUID NULL,
  from_location_id UUID NULL,
  to_location_id UUID NULL,
  quantity INTEGER NULL,
  reason VARCHAR(255) NULL,
  actor_id UUID NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),

  -- Constraints y Llaves Foráneas
  CONSTRAINT fk_event_owner FOREIGN KEY (owner_id) REFERENCES owners(owner_id),
  CONSTRAINT fk_event_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id),
  CONSTRAINT fk_event_container FOREIGN KEY (container_id) REFERENCES inventory_containers(container_id),
  CONSTRAINT fk_event_container_line FOREIGN KEY (container_line_id) REFERENCES container_lines(container_line_id),
  CONSTRAINT fk_event_product FOREIGN KEY (product_id) REFERENCES products(product_id),
  CONSTRAINT fk_event_lot FOREIGN KEY (lot_id) REFERENCES lots(lot_id),
  CONSTRAINT fk_event_from_location FOREIGN KEY (from_location_id) REFERENCES locations(location_id),
  CONSTRAINT fk_event_to_location FOREIGN KEY (to_location_id) REFERENCES locations(location_id)

);

-- Índices críticos para reporter

CREATE INDEX idx_locations_warehouse ON locations (warehouse_id);

CREATE INDEX idx_container_lines_product ON container_lines (product_id);

CREATE INDEX idx_container_active ON container_lines (container_id, qty_available);

CREATE INDEX idx_events_container ON inventory_containers (container_id);

-- =========================
-- MODIFY INVENTORY CONTAINERS TABLE
-- =========================
ALTER TABLE inventory_containers
ALTER COLUMN location_id DROP NOT NULL;

-- =========================
-- MODIFY LOTS TABLE
-- =========================
ALTER TABLE lots
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT now()
