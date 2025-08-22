# CIEMPIÉS - Sistema de Gestión Integral

## Descripción

CIEMPIÉS es un sistema de gestión integral para el programa de movilidad infantil liderado por la Secretaría de Educación del Distrito y la Secretaría Distrital de Movilidad. El sistema permite gestionar estudiantes, rutas, asistencias y usuarios del programa.

## Características

### Backend (Java Spring Boot)
- **Framework**: Spring Boot 3.2.0
- **Base de datos**: MySQL 8.0
- **Autenticación**: JWT (JSON Web Tokens)
- **Seguridad**: Spring Security con roles y permisos
- **API**: RESTful con documentación Swagger/OpenAPI
- **Validación**: Bean Validation
- **ORM**: JPA/Hibernate

### Frontend (Mejorado)
- **Framework**: Vue.js 3
- **CSS**: Tailwind CSS
- **Iconos**: Font Awesome
- **HTTP Client**: Axios
- **Responsive**: Diseño adaptativo

## Estructura del Proyecto

```
Ciempies_SGI/
├── src/
│   └── main/
│       ├── java/com/ciempies/sgi/
│       │   ├── config/          # Configuraciones
│       │   ├── controller/      # Controladores REST
│       │   ├── dto/            # Data Transfer Objects
│       │   ├── entity/         # Entidades JPA
│       │   ├── repository/     # Repositorios JPA
│       │   ├── service/        # Lógica de negocio
│       │   ├── security/       # Configuración de seguridad
│       │   └── exception/      # Manejo de excepciones
│       └── resources/
│           └── application.yml # Configuración de la aplicación
├── views/                      # Frontend (formularios mejorados)
├── assets/                     # Recursos estáticos
├── database/                   # Scripts de base de datos
├── pom.xml                     # Dependencias Maven
└── README.md                   # Este archivo
```

## Requisitos Previos

### Software Necesario
- **Java**: JDK 17 o superior
- **Maven**: 3.6 o superior
- **MySQL**: 8.0 o superior
- **Node.js**: 14 o superior (opcional, para desarrollo)

### Herramientas de Desarrollo
- **IDE**: IntelliJ IDEA, Eclipse, o VS Code
- **Base de datos**: MySQL Workbench, phpMyAdmin, o similar

## Instalación

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd Ciempies_SGI
```

### 2. Configurar la Base de Datos

#### Crear la Base de Datos
```sql
CREATE DATABASE ciempies CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### Importar el Esquema
```bash
mysql -u root -p ciempies < database/ciempies.sql/ciempies.sql
```

### 3. Configurar la Aplicación

#### Editar application.yml
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ciempies?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: tu_usuario
    password: tu_contraseña
```

#### Variables de Entorno (Opcional)
```bash
export MAIL_USERNAME=tu_email@gmail.com
export MAIL_PASSWORD=tu_contraseña_de_aplicacion
```

### 4. Compilar y Ejecutar

#### Con Maven
```bash
# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run
```

#### Con IDE
1. Importar el proyecto como proyecto Maven
2. Ejecutar la clase `CiempiesSgiApplication`

### 5. Verificar la Instalación

La aplicación estará disponible en:
- **API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **Documentación API**: http://localhost:8080/api/api-docs

## Configuración de Usuarios Iniciales

### Crear Usuario Administrador
```sql
INSERT INTO usuarios (nombre, correo, contraseña, rol, activo, fecha_creacion) 
VALUES (
    'Administrador', 
    'admin@ciempies.gov.co', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', -- password: password
    'ADMINISTRADOR', 
    true, 
    NOW()
);
```

### Credenciales por Defecto
- **Email**: admin@ciempies.gov.co
- **Contraseña**: password

## Uso del Sistema

### 1. Autenticación

#### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
    "correo": "admin@ciempies.gov.co",
    "contraseña": "password"
}
```

#### Respuesta
```json
{
    "success": true,
    "message": "Login exitoso",
    "data": {
        "token": "eyJhbGciOiJIUzUxMiJ9...",
        "tipoToken": "Bearer",
        "usuario": {
            "id": 1,
            "nombre": "Administrador",
            "correo": "admin@ciempies.gov.co",
            "rol": "ADMINISTRADOR"
        }
    }
}
```

### 2. Gestión de Estudiantes

#### Crear Estudiante
```bash
POST /api/estudiantes
Authorization: Bearer <token>
Content-Type: application/json

{
    "nombre": "Juan",
    "apellido": "Pérez",
    "documento": "1234567890",
    "sexo": "MASCULINO",
    "edad": 12,
    "telefono": "3001234567",
    "curso": "Sexto",
    "eps": "EPS Sura",
    "direccion": "Calle 123 #45-67",
    "rutaId": 1
}
```

#### Obtener Estudiantes
```bash
GET /api/estudiantes?page=0&size=10
Authorization: Bearer <token>
```

#### Buscar Estudiantes
```bash
GET /api/estudiantes/search?busqueda=juan&page=0&size=10
Authorization: Bearer <token>
```

### 3. Gestión de Rutas

#### Obtener Rutas Activas
```bash
GET /api/rutas/activas
Authorization: Bearer <token>
```

#### Crear Ruta
```bash
POST /api/rutas
Authorization: Bearer <token>
Content-Type: application/json

{
    "codRuta": "RT001",
    "nombreRuta": "Ruta Norte",
    "descripcion": "Ruta que cubre el sector norte",
    "capacidadMaxima": 30
}
```

### 4. Gestión de Asistencias

#### Registrar Asistencia
```bash
POST /api/asistencias
Authorization: Bearer <token>
Content-Type: application/json

{
    "estudianteId": 1,
    "rutaId": 1,
    "estado": "PRESENTE",
    "fecha": "2024-01-15",
    "horaInicio": "07:00:00",
    "horaFinal": "08:30:00",
    "observaciones": "Llegó puntual"
}
```

## Roles y Permisos

### ADMINISTRADOR
- Acceso completo a todas las funcionalidades
- Gestión de usuarios
- Configuración del sistema

### APOYO_TERRITORIAL
- Gestión de estudiantes
- Gestión de rutas
- Reportes y estadísticas

### MONITOR
- Consulta de estudiantes
- Registro de asistencias
- Reportes básicos

### COORDINADOR
- Supervisión de rutas
- Reportes de gestión
- Coordinación de actividades

## Formularios Mejorados

### Características de los Nuevos Formularios
- **Diseño Responsive**: Adaptable a diferentes dispositivos
- **Validación en Tiempo Real**: Validación del lado del cliente
- **Interfaz Moderna**: Diseño con Tailwind CSS y Font Awesome
- **Experiencia de Usuario**: Feedback visual y mensajes de error claros
- **Integración con API**: Comunicación directa con el backend Java

### Archivos de Formularios Mejorados
- `views/form_estudiante_moderno.html` - Formulario de registro de estudiantes
- `views/login_moderno.html` - Formulario de login mejorado
- `views/dashboard_moderno.html` - Dashboard principal

## API Endpoints

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar usuario
- `GET /api/auth/validate` - Validar token
- `POST /api/auth/logout` - Cerrar sesión

### Estudiantes
- `GET /api/estudiantes` - Listar estudiantes (paginado)
- `GET /api/estudiantes/all` - Listar todos los estudiantes
- `GET /api/estudiantes/{id}` - Obtener estudiante por ID
- `POST /api/estudiantes` - Crear estudiante
- `PUT /api/estudiantes/{id}` - Actualizar estudiante
- `DELETE /api/estudiantes/{id}` - Eliminar estudiante
- `GET /api/estudiantes/search` - Buscar estudiantes
- `GET /api/estudiantes/ruta/{idRuta}` - Estudiantes por ruta
- `GET /api/estudiantes/curso/{curso}` - Estudiantes por curso

### Rutas
- `GET /api/rutas` - Listar rutas
- `GET /api/rutas/activas` - Rutas activas
- `POST /api/rutas` - Crear ruta
- `PUT /api/rutas/{id}` - Actualizar ruta
- `DELETE /api/rutas/{id}` - Eliminar ruta

### Asistencias
- `GET /api/asistencias` - Listar asistencias
- `POST /api/asistencias` - Registrar asistencia
- `PUT /api/asistencias/{id}` - Actualizar asistencia
- `GET /api/asistencias/fecha/{fecha}` - Asistencias por fecha
- `GET /api/asistencias/ruta/{idRuta}/fecha/{fecha}` - Asistencias por ruta y fecha

## Desarrollo

### Estructura de Desarrollo
```
src/main/java/com/ciempies/sgi/
├── config/          # Configuraciones de Spring
├── controller/      # Controladores REST
├── dto/            # Objetos de transferencia de datos
├── entity/         # Entidades JPA
├── repository/     # Repositorios de datos
├── service/        # Lógica de negocio
├── security/       # Configuración de seguridad
└── exception/      # Manejo de excepciones
```

### Convenciones de Código
- **Nombres de clases**: PascalCase
- **Nombres de métodos**: camelCase
- **Nombres de variables**: camelCase
- **Constantes**: UPPER_SNAKE_CASE
- **Paquetes**: lowercase

### Testing
```bash
# Ejecutar tests
mvn test

# Ejecutar tests con cobertura
mvn jacoco:report
```

## Despliegue

### Producción
```bash
# Crear JAR ejecutable
mvn clean package

# Ejecutar en producción
java -jar target/sgi-backend-1.0.0.jar
```

### Docker (Opcional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/sgi-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

## Mantenimiento

### Logs
Los logs se encuentran en:
- **Desarrollo**: Consola
- **Producción**: `/var/log/ciempies/`

### Backup de Base de Datos
```bash
# Backup completo
mysqldump -u root -p ciempies > backup_ciempies_$(date +%Y%m%d_%H%M%S).sql

# Restaurar backup
mysql -u root -p ciempies < backup_ciempies_20240115_143022.sql
```

### Monitoreo
- **Health Check**: http://localhost:8080/api/actuator/health
- **Métricas**: http://localhost:8080/api/actuator/metrics
- **Info**: http://localhost:8080/api/actuator/info

## Soporte

### Contacto
- **Email**: soporte@ciempies.gov.co
- **Teléfono**: +57 1 123 4567
- **Documentación**: [Wiki del Proyecto]

### Reportar Issues
1. Crear un issue en el repositorio
2. Incluir descripción detallada del problema
3. Adjuntar logs y capturas de pantalla si es necesario

## Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

---

**CIEMPIÉS - Sistema de Gestión Integral**  
*Desarrollado para la Secretaría de Educación del Distrito*  
*Versión 1.0.0*
