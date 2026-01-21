# WMS Core

## Requisitos
- Java 21
- Git

## Arranque local (SIN DB externa)
```bash
mvn clean package -DskipTests
mvn spring-boot:run -Dspring-boot.run.profiles=local
