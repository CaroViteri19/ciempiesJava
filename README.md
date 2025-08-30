# CIEMPIÉS SGI - Sistema de Gestión Integral

Sistema de gestión integral para CIEMPIÉS desarrollado en Spring Boot.

## Requisitos

- Java 17+
- Maven 3.6+
- MySQL 8.0+ (XAMPP)

## Configuración Rápida

### 1. Base de Datos
```sql
CREATE DATABASE ciempies CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```
- Importar: `database/ciempies.sql/ciempies.sql`

### 2. Ejecutar
```bash
# Opción 1: Script
run_application.bat

# Opción 2: Manual
mvn spring-boot:run
```

### 3. Acceder
- **URL:** http://localhost:8080
- **Login:** marcela1@cienpies.co / 1234

## Estructura

```
src/main/java/com/ciempies/sgi/
├── CiempiesSgiApplication.java    # Main
├── controller/                    # Controladores
├── entity/                       # Entidades JPA
└── repository/                   # Repositorios
```

## Tecnologías

- Spring Boot 3.2.0
- MySQL + JPA/Hibernate
- Thymeleaf + Tailwind CSS
- Maven
