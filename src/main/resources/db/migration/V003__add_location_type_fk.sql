-- =========================
-- LOCATIONS TYPES
-- =========================
CREATE TABLE location_types (
  type_id UUID PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  indicator VARCHAR(10) NOT NULL ,
  active BOOLEAN NOT NULL DEFAULT true,
  created_at TIMESTAMP NOT NULL DEFAULT now(),

  CONSTRAINT uq_location_type_name UNIQUE(name),
  CONSTRAINT uq_location_type_indicator UNIQUE(indicator)
);

-- =========================
-- MODIFY LOCATIONS TABLE
-- =========================

-- eliminar columna antigua
ALTER TABLE locations
DROP COLUMN type;

-- agregar nueva columna
ALTER TABLE locations
ADD COLUMN type_id UUID NOT NULL;

-- FK hacia location_types
ALTER TABLE locations
ADD CONSTRAINT fk_location_type
FOREIGN KEY (type_id) REFERENCES location_types(type_id);

-- =========================
-- SEED INITIAL TYPES
-- =========================
INSERT INTO location_types (type_id, name, indicator)
VALUES
(RANDOM_UUID(), 'PASILLO', 'PA'),
(RANDOM_UUID(), 'RACK', 'RK'),
(RANDOM_UUID(), 'BIN', 'BIN');
