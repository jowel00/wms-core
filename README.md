# 📦 WMS Core - Smart Inventory Suite

> **Núcleo Logístico Evolutivo desarrollado por Vision Boosters.**

Este es el backend central (WMS Core) diseñado para **DeRocha Store**. No es un ERP tradicional ni un software de inventario básico; es una plataforma construida bajo los principios de **Domain-Driven Design (DDD)** y **Event Sourcing** (trazabilidad por eventos), preparada para escalar a múltiples bodegas y manejar decenas de miles de referencias sin perder una sola caja.

## 🚀 Estado Actual: Fase 1 (Stock y Fundaciones)
El objetivo de esta fase es **orden, control y confianza**.
Actualmente el sistema permite:
- Gestión Multi-Tenant (Owners/Clientes).
- Administración de Bodegas y Ubicaciones físicas codificadas.
- Catálogo de Productos con validación estricta de SKUs.
- Gestión de Inventario encapsulado (Todo vive en contenedores lógicos, nunca suelto).
- Auditoría 100% inmutable (Append-only events).

---

## 🛠️ Stack Tecnológico
- **Framework:** Java 17+ con Spring Boot 3.x
- **Base de Datos:** PostgreSQL 16
- **Control de Versiones DB:** Flyway
- **Persistencia:** Spring Data JPA
- **Infraestructura Local:** Docker Compose

---

## ⚙️ Guía de Inicio Rápido (Local Setup)

¡Alto ahí! 🛑 **NO intentes instalar PostgreSQL a mano ni configurar variables sueltas.** El entorno local está dockerizado para que funcione a la primera.

### 1. Pre-requisitos
- Tener instalado [Docker Desktop](https://www.docker.com/products/docker-desktop/).
- Tener instalado Java 17 o superior y Maven.

### 2. Levantar la Base de Datos
En la raíz del proyecto, abre tu terminal y ejecuta:
```bash
docker-compose up -d

Esto descargará y levantará un contenedor de PostgreSQL en el puerto 5432 con la base de datos wms_db y las credenciales correctas.

3. Arrancar la Aplicación
Puedes darle "Play" desde tu IDE (IntelliJ, VS Code, Eclipse) o correr:

mvn spring-boot:run -Dspring-boot.run.profiles=dev

Magia de Flyway: Al arrancar, Spring Boot se conectará a PostgreSQL y Flyway ejecutará automáticamente todos los scripts SQL en la carpeta src/main/resources/db/migration. No tienes que crear tablas manualmente.

🧠 Reglas de Oro de la Arquitectura (¡A leer antes de codear!)
Para mantener la integridad del sistema, todo desarrollador debe respetar estos principios inquebrantables:

Nada existe sin contexto físico: Todo producto debe estar dentro de un InventoryContainer (Caja/Tote/Pallet). No existe el concepto de "inventario suelto".

Las cantidades NUNCA son negativas: Invariante matemática del dominio.

Event Sourcing (Append-Only): El estado de un contenedor solo cambia si se genera un evento (InventoryEvent). Nunca se hace un UPDATE a una cantidad sin registrar el quién, cuándo y por qué.

Respetar Flyway: Prohibido usar spring.jpa.hibernate.ddl-auto: update. Las tablas solo se modifican creando nuevos scripts Vxxx__nombre.sql en la carpeta de migraciones.

Idempotencia y Multi-tenant: Todo request debe estar validado contra el owner_id. Los clientes no pueden ver ni tocar cajas de otros clientes.

📂 Estructura del Proyecto (DDD Liviano)
El código está organizado por dominios de negocio, no por capas técnicas vacías:

src/main/java/com/wms/core/
├── domain/               # El corazón: Entidades, Reglas de Negocio y Servicios de Dominio
│   ├── catalog/          # Productos, Lotes
│   ├── warehouse/        # Bodegas, Ubicaciones físicas (Racks)
│   ├── inventory/        # Contenedores, Movimientos, Cantidades
│   └── events/           # Motor de auditoría inmutable
├── application/          # Casos de uso y orquestación
└── infrastructure/       # El mundo exterior: Controladores REST, Repositorios JPA, Configs

📡 Endpoints Principales (Fase 1)
Nota: Todos los endpoints requieren el header Authorization y se sirven bajo /api/v1.

Método,Endpoint,Descripción
POST,/products/bulk-upload,Carga masiva de catálogo vía CSV (Optimizado con JPA Batching).
POST,/warehouses,Crea una nueva bodega para un Owner.
POST,/locations,Crea una ubicación física (ej. RACK-001).
POST,/inventory/receive,Recepción de proveedor. Crea un contenedor en estado CREATED.
POST,/inventory/putaway,Ubica un contenedor en un Rack (Pasa a estado ACTIVE).
POST,/inventory/adjust,Ajuste manual de stock (Requiere motivo y rol de supervisor).

🛡️ Riesgos Mitigados
Stock Fantasma: Resuelto mediante flujos de QUARANTINE para devoluciones.

Cuellos de Botella DB: Resuelto mediante JPA Batching (1000 inserts/lote) y carga de Paginación obligatoria en el Frontend.

Errores de Escaneo: Interfaz Scanner-first obligatoria para operarios de bodega.

Desarrollado con disciplina por el equipo de Vision Boosters. 🚀