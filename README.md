# CIEMPIÉS SGI - Sistema de Gestión Integral

Sistema de gestión integral para CIEMPIÉS desarrollado en Spring Boot con Java 17.

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- XAMPP (para MySQL)

## Configuración de la Base de Datos

### 1. Iniciar MySQL
- Abrir XAMPP Control Panel
- Iniciar el servicio de MySQL
- Verificar que MySQL esté corriendo en el puerto 3306

### 2. Crear la Base de Datos
```sql
-- Ejecutar en phpMyAdmin o MySQL Workbench
CREATE DATABASE IF NOT EXISTS `ciempies` 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_general_ci;
```

### 3. Importar el Esquema
- Abrir phpMyAdmin (http://localhost/phpmyadmin)
- Seleccionar la base de datos `ciempies`
- Ir a la pestaña "Importar"
- Seleccionar el archivo `database/ciempies.sql/ciempies.sql`
- Hacer clic en "Continuar"

## Configuración de la Aplicación

### 1. Verificar Configuración
El archivo `src/main/resources/application.properties` ya está configurado para:
- Base de datos: `ciempies`
- Usuario: `root`
- Contraseña: (vacía)
- Puerto: `8080`

### 2. Credenciales de Prueba
Puedes usar estas credenciales para probar el login:
- **Email:** marcela1@cienpies.co
- **Contraseña:** 1234

## Ejecutar la Aplicación

### Opción 1: Desde la línea de comandos
```bash
# Navegar al directorio del proyecto
cd c:\xampp\htdocs\ciempiesJava

# Compilar y ejecutar
mvn spring-boot:run
```

### Opción 2: Desde un IDE
- Abrir el proyecto en IntelliJ IDEA, Eclipse o VS Code
- Ejecutar la clase `CiempiesSgiApplication`

## Acceder a la Aplicación

Una vez que la aplicación esté corriendo:

1. **Página Principal:** http://localhost:8080
2. **Login:** http://localhost:8080/login
3. **Dashboard:** http://localhost:8080/dashboard (requiere login)

## Estructura del Proyecto

```
src/main/java/com/ciempies/sgi/
├── CiempiesSgiApplication.java    # Clase principal
├── config/
│   └── WebConfig.java            # Configuración web
├── controller/
│   ├── AuthController.java       # Autenticación
│   ├── ViewController.java       # Vistas
│   └── ...                       # Otros controladores
├── entity/
│   ├── Usuario.java             # Entidad Usuario
│   └── ...                      # Otras entidades
└── repository/
    ├── UsuarioRepository.java    # Repositorio Usuario
    └── ...                       # Otros repositorios
```

## Funcionalidades Implementadas

- ✅ **Autenticación:** Login con email y contraseña
- ✅ **Dashboard:** Vista principal con estadísticas
- ✅ **Gestión de Usuarios:** CRUD completo
- ✅ **Gestión de Estudiantes:** CRUD completo
- ✅ **Gestión de Rutas:** CRUD completo
- ✅ **Gestión de Asistencias:** CRUD completo
- ✅ **Gestión de Inscripciones:** CRUD completo
- ✅ **Gestión de Zonas:** CRUD completo

## Solución de Problemas

### Error de Conexión a la Base de Datos
1. Verificar que MySQL esté corriendo en XAMPP
2. Verificar que la base de datos `ciempies` exista
3. Verificar las credenciales en `application.properties`

### Error de Puerto en Uso
Si el puerto 8080 está ocupado, cambiar en `application.properties`:
```properties
server.port=8081
```

### Error de Compilación
1. Verificar que Java 17 esté instalado: `java -version`
2. Limpiar y recompilar: `mvn clean install`

## Tecnologías Utilizadas

- **Backend:** Spring Boot 3.2.0
- **Base de Datos:** MySQL 8.0
- **ORM:** JPA/Hibernate
- **Frontend:** Thymeleaf + Tailwind CSS
- **Build Tool:** Maven
- **Java Version:** 17

## Contacto

Para soporte técnico o consultas sobre el sistema CIEMPIÉS SGI.
