# WMS Core — Smart Inventory Suite

Backend central del sistema de gestión de almacenes (WMS) desarrollado por **Vision Boosters** para **DeRocha Store**.

Construido bajo principios de **Domain-Driven Design (DDD)** con arquitectura de dos capas, diseñado para escalar a múltiples bodegas y manejar decenas de miles de referencias de producto con trazabilidad completa.

---

## Stack Tecnológico

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.5.0 |
| Base de datos (producción) | PostgreSQL 16 |
| Base de datos (local) | H2 in-memory |
| Migraciones | Flyway |
| Persistencia | Spring Data JPA |
| Infraestructura local | Docker Compose |
| Parsing CSV | OpenCSV |
| Reducción de boilerplate | Lombok |

---

## Inicio Rápido

### Prerrequisitos

- Java 21+
- Maven 3.9+ (o usar el wrapper incluido `./mvnw`)
- Docker Desktop (solo para perfil `dev`/producción con PostgreSQL)

### Opción A — Entorno local sin base de datos externa (H2 in-memory)

No requiere Docker. Utiliza una base de datos en memoria que se crea al iniciar la aplicación.

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

La aplicación queda disponible en `http://localhost:8080`. Los datos se pierden al detener la aplicación.

### Opción B — Entorno con PostgreSQL (Docker)

**1. Levantar la base de datos:**

```bash
docker-compose up -d
```

Esto levanta un contenedor PostgreSQL 16 en el puerto `5432` con:
- Usuario: `wms_user`
- Contraseña: `wms_pass`
- Base de datos: `wms_db`

**2. Arrancar la aplicación:**

```bash
./mvnw spring-boot:run
```

Flyway ejecutará automáticamente las migraciones en `src/main/resources/db/migration/`.

---

## Comandos de Desarrollo

```bash
# Compilar sin ejecutar tests
./mvnw clean package -DskipTests

# Ejecutar todos los tests
./mvnw test

# Ejecutar una clase de test específica
./mvnw test -Dtest=NombreClase

# Ejecutar un método de test específico
./mvnw test -Dtest=NombreClase#nombreMetodo
```

---

## Arquitectura

El proyecto sigue una arquitectura de dos capas inspirada en DDD:

```
src/main/java/com/wms/core/
├── application/                # Casos de uso
│   ├── mapper/                 # Mapeo entre entidades de dominio y DTOs
│   └── service/                # Servicios de aplicacion,orquestacion casos de uso
│                
├── domain/                     # Núcleo de negocio
│   ├── catalog/                # Entidad Product
│   ├── exception/              # Excepciones de dominio
│   ├── inventory/              # Entidades InventoryContainer, ContainerLine yLot
│   ├── owner/                  # Entidad Owner
│   └── warehouse/              # Entidades Warehouse y Location
│                
└── infrastructure/             # Adaptadores externos
│   └── persistence/            # Repositorios Spring Data JPA
│   └── web/
│       ├── config/             # Configuración CORS
│       ├── controller/         # Controladores REST
│       ├── dto/                # Request y Response DTOs
│       ├── exception/          # Manejo centralizado de errores
│       └── mapper/             # Mapeo centralizado de errores
```

### Modelo de Dominio
- **Owner** — Representa un cliente/tenant del sistema. Puede tener hasta 2 bodegas.
- **Warehouse** — Bodega física asociada a un Owner. Tiene país y ciudad.
- **Location Type** - Define los tipos de ubicaciones disponibles dentro de una bodega. Tiene nombre e identificador único.
- **Location** — Ubicación física dentro de una bodega (rack, pasillo, zona). Soporta jerarquía padre-hijo. El código de ubicación es único por bodega.
- **Inventory Container** - Contenedor logico de inventario (BOX, TOTE, PALLET). Asociado a un Owner, Warehouse y Location.
- **Product** — Referencia de producto del catálogo. Asociado a un Owner con SKU único por tenant.

### Flujo de Request

```
Controller → @Valid → Service → Repository → Entity → Response DTO
```

### Manejo de Errores

`GlobalExceptionHandler` centraliza todas las respuestas de error HTTP:

| Excepción                   | HTTP Status                                              |
|-----------------------------|----------------------------------------------------------|
| `BusinessRuleException`     | 400 Bad Request                                          |
| `ResourceNotFoundException` | 404 Not Found                                            |
| `ResourceConflictException` | 409 Conflict                                             |
| `CsvParseException`         | 422 Unprocessable Entity (con lista de errores por fila) |

---

## API REST

### Owners

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/owners` | Crear un nuevo owner |
| `GET` | `/owners/{id}` | Obtener owner por ID |

**POST /owners — Body:**
```json
{
  "name": "DeRocha Store"
}
```

### Warehouses

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/warehouses` | Crear una bodega para un owner (máximo 2 por owner) |
| `GET` | `/warehouses?ownerId={UUID}` | Listar bodegas de un owner |

**POST /warehouses — Body:**
```json
{
  "ownerId": "uuid-del-owner",
  "name": "Bodega Principal",
  "countryCode": "CO",
  "city": "Bogotá"
}
```
### Locations Types

| Método | Endpoint                  | Descripción                                       |
|---|---------------------------|---------------------------------------------------|
| `POST` | `/locations-types`        | Crear un tipo de bodega       |
| `POST` | `/locations-types/bulk`   | Crear diferentes tipos de bodegas al mismo tiempo |
| `GET` | `/locations-types`        | Listar tipos de bodega                            |

**POST /locations-types — Body:**
```json
{
  "name": "Pasillo",
  "indicator": "PA"
}
```

### Locations

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/locations` | Crear una ubicación física en una bodega |
| `GET` | `/locations?warehouseId={UUID}` | Listar ubicaciones de una bodega |

**POST /locations — Body:**
```json
{
  "warehouseId": "uuid-de-la-bodega",
  "typeId": "uuid-del-tipo-de-bodega",
  "parentLocationId": null
}
```

### Products

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/v1/products/bulk-upload` | Carga masiva de catálogo desde archivo CSV |

**POST /api/v1/products/bulk-upload — Form-data:**

| Campo | Tipo | Descripción |
|---|---|---|
| `ownerId` | UUID | ID del owner al que pertenecen los productos |
| `file` | File (CSV) | Archivo CSV con el catálogo de productos |

**Formato del CSV:**

```csv
seller_sku,name,barcode
SKU-001,Camiseta Blanca Talla M,7891234567890
SKU-002,Pantalón Negro Talla 32,7891234567891
```

- Columnas obligatorias: `seller_sku`, `name`
- Columna opcional: `barcode`
- Los encabezados son case-insensitive
- El sistema valida duplicados dentro del archivo y contra la base de datos
- La carga es **transaccional**: si una fila falla, no se guarda ningún registro
- Optimizado con JPA batch insert (lotes de 1.000 registros)

**Respuesta exitosa:**
```json
{
  "message": "Carga masiva completada exitosamente",
  "productsCreated": 20000
}
```

---

## Reglas de Negocio

| Regla | Descripción |
|---|---|
| Máximo 2 bodegas por owner | Validado en `WarehouseService` |
| Código de ubicación único por bodega | Restricción a nivel de base de datos y validación en servicio |
| SKU único por owner | Validado antes del insert en carga masiva |
| Transaccionalidad en carga masiva | Un error en cualquier fila revierte toda la operación |
| Multi-tenant | Los recursos de un owner son completamente aislados |

---

## Migraciones de Base de Datos

Las migraciones se gestionan con Flyway y están en `src/main/resources/db/migration/`.

**Regla importante:** No usar `spring.jpa.hibernate.ddl-auto=update`. Cualquier cambio al esquema debe hacerse creando un nuevo script con el formato `V{numero}__{descripcion}.sql`.

El perfil `local` deshabilita Flyway y usa el esquema generado por Hibernate directamente sobre H2.

---

## Configuración Docker

El archivo `docker-compose.yml` levanta únicamente la base de datos PostgreSQL. La aplicación se ejecuta en el host de manera independiente.

```yaml
# Variables de conexión configuradas en el contenedor:
POSTGRES_USER: wms_user
POSTGRES_PASSWORD: wms_pass
POSTGRES_DB: wms_db
# Puerto expuesto: 5432
```

Los datos persisten en el volumen Docker `pgdata`.

---

Desarrollado por el equipo de **Vision Boosters**.
