# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

**Build (skip tests):**
```bash
./mvnw clean package -DskipTests
```

**Run locally (H2 in-memory DB, no external database required):**
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

**Run all tests:**
```bash
./mvnw test
```

**Run a single test class:**
```bash
./mvnw test -Dtest=ClassName
```

**Run a single test method:**
```bash
./mvnw test -Dtest=ClassName#methodName
```

## Architecture

Spring Boot 3.5.0 REST API (Java 21) using a two-layer structure:

- **`domain/`** — Entities and business services
- **`infrastructure/`** — Persistence (Spring Data JPA repositories) and Web (controllers, DTOs, exception handling)

### Entities and Relationships

- **Owner** → has many **Warehouse** (max 2 per owner, enforced in `WarehouseService`)
- **Warehouse** → has many **Location** (unique constraint on `warehouse_id + code`)
- **Location** → self-referencing `parentLocation` (supports hierarchical structure), belongs to a Warehouse

### Request Flow

`Controller` → validates via `@Valid` → calls `Service` → calls `Repository` → returns entity → mapped to response DTO in controller or service.

### Database

- **Production:** PostgreSQL (configured via environment/properties)
- **Local profile:** H2 in-memory (`jdbc:h2:mem:localdb`), Flyway disabled
- **Migrations:** Flyway, located in `src/main/resources/db/migration/`

### REST Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/owners` | Create owner |
| GET | `/owners/{id}` | Get owner by ID |
| POST | `/warehouses` | Create warehouse |
| GET | `/warehouses?ownerId=<UUID>` | List warehouses by owner |
| POST | `/locations` | Create location |
| GET | `/locations?warehouseId=<UUID>` | List locations by warehouse |

### Exception Handling

`GlobalExceptionHandler` centralizes all HTTP error responses. Business rule violations throw `IllegalArgumentException` (mapped to 400). Entity not found throws `OwnerNotFoundException` (mapped to 404).
