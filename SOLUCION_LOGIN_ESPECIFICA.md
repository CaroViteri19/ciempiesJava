# 🔧 SOLUCIÓN ESPECÍFICA AL PROBLEMA DE LOGIN

## 🚨 **Problema Identificado:**
No puedes ingresar con el usuario `admin@ciempies.com` y contraseña `admin123`

## ✅ **Verificaciones Realizadas:**

### 1. **Base de Datos:**
- ✅ Base de datos `ciempies_sgi` existe
- ✅ Usuario `admin@ciempies.com` existe en la tabla `usuarios`
- ✅ Contraseña encriptada correctamente con BCrypt
- ✅ Rol: `ADMIN`
- ✅ Estado: `activo = true`

### 2. **Configuración:**
- ✅ `application.yml` configurado correctamente
- ✅ Conexión a MySQL en puerto 3306
- ✅ Usuario: `root`, Contraseña: vacía
- ✅ Driver MySQL configurado

## 🛠️ **SOLUCIÓN PASO A PASO:**

### **PASO 1: Ejecutar Diagnóstico Completo**
```bash
diagnostico_completo.bat
```

### **PASO 2: Si el Backend No Inicia**
1. **Verificar puerto 8080:**
   ```bash
   netstat -ano | findstr :8080
   ```

2. **Si está ocupado, matar el proceso:**
   ```bash
   taskkill /PID [PID] /F
   ```

3. **Ejecutar backend manualmente:**
   ```bash
   C:\maven\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
   ```

### **PASO 3: Probar Login**
1. **Abrir navegador:**
   ```
   http://localhost/Ciempies_SGI/test_login.html
   ```

2. **Usar credenciales:**
   - **Usuario:** `admin@ciempies.com`
   - **Contraseña:** `admin123`

### **PASO 4: Si Sigue Sin Funcionar**

#### **Opción A: Recrear Base de Datos**
```bash
# Eliminar base de datos existente
C:\xampp\mysql\bin\mysql.exe -u root -e "DROP DATABASE IF EXISTS ciempies_sgi;"

# Crear nueva base de datos
Get-Content database/setup_database.sql | C:\xampp\mysql\bin\mysql.exe -u root
```

#### **Opción B: Verificar Logs del Backend**
1. Ejecutar backend y ver logs:
   ```bash
   C:\maven\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
   ```

2. Buscar errores específicos:
   - Errores de conexión a base de datos
   - Errores de autenticación
   - Errores de JWT

#### **Opción C: Probar API Directamente**
1. **Verificar que el backend esté ejecutándose:**
   ```
   http://localhost:8080/api/swagger-ui.html
   ```

2. **Probar endpoint de login:**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
   -H "Content-Type: application/json" \
   -d "{\"correo\":\"admin@ciempies.com\",\"contraseña\":\"admin123\"}"
   ```

## 🔍 **DIAGNÓSTICO ADICIONAL:**

### **Verificar en phpMyAdmin:**
1. Abrir phpMyAdmin: `http://localhost/phpmyadmin`
2. Seleccionar base de datos `ciempies_sgi`
3. Ir a tabla `usuarios`
4. Verificar que existe el usuario con:
   - `correo`: `admin@ciempies.com`
   - `contrasena`: `$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa`
   - `rol`: `ADMIN`
   - `activo`: `1`

### **Verificar Logs de Spring Boot:**
Buscar en la consola donde ejecutaste `mvn spring-boot:run`:
- `Hibernate: select usuario0_.id as id1_0_` (debe aparecer)
- `Authentication successful` (debe aparecer)
- `JWT token generated` (debe aparecer)

## 🎯 **SOLUCIÓN RÁPIDA:**

### **Script Automático:**
```bash
# 1. Ejecutar diagnóstico
diagnostico_completo.bat

# 2. Si todo está OK, el backend se iniciará automáticamente

# 3. Probar login en:
http://localhost/Ciempies_SGI/test_login.html
```

## 📞 **Si Nada Funciona:**

### **Verificar:**
1. **XAMPP está ejecutándose** (MySQL y Apache)
2. **Puerto 8080 libre**
3. **Java 17 instalado**
4. **Maven instalado**
5. **Base de datos creada**
6. **Usuario admin existe**

### **Comandos de Verificación:**
```bash
# Verificar MySQL
C:\xampp\mysql\bin\mysql.exe -u root -e "SELECT VERSION();"

# Verificar usuario
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT * FROM usuarios WHERE correo='admin@ciempies.com';"

# Verificar Java
java -version

# Verificar Maven
C:\maven\apache-maven-3.9.6\bin\mvn.cmd -version

# Verificar puerto
netstat -ano | findstr :8080
```

---

**¡Con estos pasos deberías poder ingresar al sistema! 🎉**
