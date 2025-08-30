# INSTRUCCIONES PARA CONECTAR CIEMPIÉS SGI

## Paso 1: Verificar Requisitos Previos

### 1.1 Java
```bash
java -version
```
**Debe mostrar Java 17 o superior**

### 1.2 Maven
```bash
mvn -version
```
**Debe mostrar Maven 3.6 o superior**

### 1.3 XAMPP
- Abrir XAMPP Control Panel
- Iniciar MySQL
- Verificar que esté corriendo en puerto 3306

## Paso 2: Configurar Base de Datos

### 2.1 Crear Base de Datos
1. Abrir phpMyAdmin: http://localhost/phpmyadmin
2. Crear nueva base de datos:
   - Nombre: `ciempies`
   - Collation: `utf8mb4_general_ci`

### 2.2 Importar Esquema
1. En phpMyAdmin, seleccionar la base de datos `ciempies`
2. Ir a pestaña "Importar"
3. Seleccionar archivo: `database/ciempies.sql/ciempies.sql`
4. Hacer clic en "Continuar"

### 2.3 Verificar Importación
Ejecutar el script `test_connection.sql` en phpMyAdmin para verificar que todo esté correcto.

## Paso 3: Configurar Aplicación

### 3.1 Verificar Configuración
El archivo `src/main/resources/application.properties` ya está configurado:
- Base de datos: `ciempies`
- Usuario: `root`
- Contraseña: (vacía)
- Puerto: `8080`

### 3.2 Credenciales de Prueba
- **Email:** marcela1@cienpies.co
- **Contraseña:** 1234

## Paso 4: Ejecutar Aplicación

### Opción A: Script Automático (Recomendado)
```bash
# En Windows
run_application.bat

# En Linux/Mac
./run_application.sh
```

### Opción B: Manual
```bash
# Navegar al directorio
cd c:\xampp\htdocs\ciempiesJava

# Compilar y ejecutar
mvn spring-boot:run
```

### Opción C: IDE
1. Abrir proyecto en IntelliJ IDEA, Eclipse o VS Code
2. Ejecutar clase `CiempiesSgiApplication`

## Paso 5: Verificar Funcionamiento

### 5.1 Acceder a la Aplicación
- **Página Principal:** http://localhost:8080
- **Login:** http://localhost:8080/login
- **Dashboard:** http://localhost:8080/dashboard

### 5.2 Probar Login
1. Ir a http://localhost:8080/login
2. Usar credenciales:
   - Email: marcela1@cienpies.co
   - Contraseña: 1234
3. Debería redirigir al dashboard

### 5.3 Verificar Funcionalidades
- Dashboard con estadísticas
- Gestión de usuarios
- Gestión de estudiantes
- Gestión de rutas
- Gestión de asistencias
- Gestión de inscripciones
- Gestión de zonas

## Paso 6: Solución de Problemas

### Error: "Connection refused"
- Verificar que MySQL esté corriendo en XAMPP
- Verificar puerto 3306

### Error: "Database not found"
- Verificar que la base de datos `ciempies` exista
- Reimportar el esquema SQL

### Error: "Invalid credentials"
- Verificar credenciales en `application.properties`
- Usar credenciales de prueba proporcionadas

### Error: "Port already in use"
- Cambiar puerto en `application.properties`:
  ```properties
  server.port=8081
  ```

### Error: "Java not found"
- Instalar Java 17 o superior
- Verificar variable PATH

### Error: "Maven not found"
- Instalar Maven 3.6 o superior
- Verificar variable PATH

## Paso 7: Verificación Final

### 7.1 Logs de Aplicación
Verificar en la consola que aparezca:
```
Started CiempiesSgiApplication in X.XXX seconds
```

### 7.2 Base de Datos
Verificar en phpMyAdmin que las tablas tengan datos:
- usuario: ~31 registros
- estudiante: ~99 registros
- asistencias: ~36 registros
- ruta: ~9 registros
- inscripcion: ~53 registros
- zona: ~10 registros

### 7.3 API Endpoints
Probar endpoints:
- GET http://localhost:8080/api/auth/usuarios
- GET http://localhost:8080/api/estudiantes
- GET http://localhost:8080/api/rutas

## ¡Listo!

El sistema CIEMPIÉS SGI está completamente conectado y funcionando.

**URLs importantes:**
- Aplicación: http://localhost:8080
- Login: http://localhost:8080/login
- Dashboard: http://localhost:8080/dashboard
- API: http://localhost:8080/api

**Credenciales de administrador:**
- Email: marcela1@cienpies.co
- Contraseña: 1234
