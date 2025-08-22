# 🚀 INSTRUCCIONES DE EJECUCIÓN - CIEMPIÉS SGI

## 📋 Requisitos Previos

### Software Necesario:
- **Java 17** o superior
- **Maven** 3.6+
- **MySQL** 8.0+
- **XAMPP** (opcional, para servir archivos HTML)

### Verificar Instalación:
```bash
java -version
mvn -version
mysql --version
```

## 🗄️ Configuración de Base de Datos

### 1. Crear Base de Datos
```sql
CREATE DATABASE ciempies_sgi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Ejecutar Script de Inicialización
```bash
mysql -u root -p ciempies_sgi < database/ciempies_init.sql
```

### 3. Verificar Configuración
Editar `src/main/resources/application.yml` si es necesario:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ciempies_sgi
    username: tu_usuario
    password: tu_contraseña
```

## 🔧 Configuración del Backend Java

### 1. Navegar al Directorio del Proyecto
```bash
cd C:\xampp\htdocs\Ciempies_SGI
```

### 2. Compilar el Proyecto
```bash
mvn clean compile
```

### 3. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

### 4. Verificar que el Backend esté Funcionando
- Abrir: `http://localhost:8080`
- Debería mostrar la página de Swagger UI
- API disponible en: `http://localhost:8080/api/`

## 🌐 Configuración del Frontend

### Opción 1: Usando XAMPP (Recomendado)
1. Iniciar XAMPP Control Panel
2. Iniciar Apache
3. Abrir: `http://localhost/Ciempies_SGI/views/index.html`

### Opción 2: Abrir Directamente
1. Navegar a: `C:\xampp\htdocs\Ciempies_SGI\views\`
2. Hacer doble clic en `index.html`

### Opción 3: Servidor Local
```bash
# Instalar servidor HTTP simple (si tienes Python)
python -m http.server 8000
# Luego abrir: http://localhost:8000/views/index.html
```

## 👤 Usuario Inicial

El sistema viene con un usuario administrador por defecto:
- **Correo**: admin@ciempies.com
- **Contraseña**: admin123
- **Rol**: ADMIN

## 📱 Páginas Disponibles

### 1. Página Principal (Login)
- **URL**: `views/index.html`
- **Funcionalidad**: Inicio de sesión con validaciones

### 2. Registro de Usuarios
- **URL**: `views/registro.html`
- **Funcionalidad**: Crear nuevas cuentas de usuario

### 3. Dashboard Principal
- **URL**: `views/dashboard.html`
- **Funcionalidad**: Panel de control con estadísticas

### 4. Gestión de Estudiantes
- **URL**: `views/form_estudiante_moderno.html`
- **Funcionalidad**: Registro y gestión de estudiantes

### 5. Control de Asistencias
- **URL**: `views/asistencia.html`
- **Funcionalidad**: Gestión completa de asistencias

### 6. Gestión de Rutas
- **URL**: `views/rutas.html`
- **Funcionalidad**: Administración de rutas de transporte

## 🔐 Roles y Permisos

### ADMIN
- Acceso completo a todas las funcionalidades
- Gestión de usuarios
- Configuración del sistema

### COORDINADOR
- Gestión de estudiantes y rutas
- Control de asistencias
- Reportes y estadísticas

### MONITOR
- Registro de asistencias
- Consulta de información básica
- Gestión limitada

## 🛠️ Solución de Problemas

### Error de Conexión a Base de Datos
```bash
# Verificar que MySQL esté ejecutándose
net start mysql

# Verificar credenciales en application.yml
# Probar conexión manual
mysql -u root -p
```

### Error de Puerto Ocupado
```bash
# Cambiar puerto en application.yml
server:
  port: 8081

# O matar proceso en puerto 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Error de CORS
- Verificar que el backend esté ejecutándose en `http://localhost:8080`
- Revisar configuración CORS en `SecurityConfig.java`

### Error de Token JWT
- Limpiar localStorage del navegador
- Volver a iniciar sesión

## 📊 API Endpoints Disponibles

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar usuario
- `GET /api/auth/validate` - Validar token

### Estudiantes
- `GET /api/estudiantes` - Listar estudiantes
- `POST /api/estudiantes` - Crear estudiante
- `PUT /api/estudiantes/{id}` - Actualizar estudiante
- `DELETE /api/estudiantes/{id}` - Eliminar estudiante

### Rutas
- `GET /api/rutas` - Listar rutas
- `POST /api/rutas` - Crear ruta
- `PUT /api/rutas/{id}` - Actualizar ruta
- `DELETE /api/rutas/{id}` - Eliminar ruta

### Asistencias
- `GET /api/asistencias` - Listar asistencias
- `POST /api/asistencias` - Crear asistencia
- `PUT /api/asistencias/{id}` - Actualizar asistencia
- `DELETE /api/asistencias/{id}` - Eliminar asistencia

### Dashboard
- `GET /api/dashboard/stats` - Estadísticas del dashboard
- `GET /api/dashboard/chart-data` - Datos para gráficos

## 🔄 Flujo de Trabajo Típico

1. **Iniciar Backend**: `mvn spring-boot:run`
2. **Iniciar Frontend**: Abrir `index.html` en navegador
3. **Iniciar Sesión**: Usar credenciales de administrador
4. **Crear Rutas**: Ir a Gestión de Rutas
5. **Registrar Estudiantes**: Asignar a rutas
6. **Controlar Asistencias**: Registrar asistencia diaria
7. **Ver Reportes**: Consultar dashboard

## 📝 Notas Importantes

- **Backend**: Debe estar ejecutándose para que el frontend funcione
- **Base de Datos**: Verificar conexión antes de usar el sistema
- **Navegador**: Usar navegadores modernos (Chrome, Firefox, Edge)
- **CORS**: Configurado para desarrollo local
- **Tokens**: Se almacenan en localStorage del navegador

## 🆘 Soporte

Si encuentras problemas:
1. Verificar logs del backend en la consola
2. Revisar consola del navegador (F12)
3. Verificar conexión a base de datos
4. Confirmar que todos los servicios estén ejecutándose

## 🎯 Próximos Pasos

1. **Personalización**: Adaptar colores y logos
2. **Funcionalidades**: Agregar reportes avanzados
3. **Seguridad**: Implementar políticas de contraseñas
4. **Backup**: Configurar respaldos automáticos
5. **Despliegue**: Preparar para producción

---

**¡El sistema CIEMPIÉS SGI está listo para usar! 🎉**
