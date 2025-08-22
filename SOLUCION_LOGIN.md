# 🔧 SOLUCIÓN AL PROBLEMA DE LOGIN - CIEMPIÉS SGI

## 🚨 Problema Identificado

El problema de login puede deberse a varios factores. Vamos a solucionarlo paso a paso.

## 📋 Pasos para Solucionar

### 1. Verificar que MySQL esté ejecutándose

**Opción A: Si usas XAMPP**
1. Abrir XAMPP Control Panel
2. Iniciar MySQL
3. Verificar que el puerto 3306 esté activo

**Opción B: Si usas MySQL standalone**
```bash
net start mysql
```

### 2. Crear la Base de Datos

Ejecuta este comando en la terminal:
```bash
mysql -u root -p < database/ciempies_init.sql
```

**Si te pide contraseña:**
- Si usas XAMPP: deja vacío (presiona Enter)
- Si configuraste contraseña: ingrésala

### 3. Verificar Configuración

El archivo `src/main/resources/application.yml` debe tener:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ciempies_sgi?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: root
    password:  # dejar vacío si usas XAMPP
```

### 4. Ejecutar el Backend

```bash
mvn spring-boot:run
```

**Verificar que el servidor inicie correctamente:**
- Debe mostrar: "Started CiempiesSgiApplication"
- No debe mostrar errores de conexión a base de datos

### 5. Probar la API

Abre en tu navegador:
```
http://localhost:8080/api/swagger-ui.html
```

Si funciona, verás la documentación de Swagger.

### 6. Probar el Login

**Credenciales por defecto:**
- **Usuario:** admin@ciempies.com
- **Contraseña:** admin123

## 🔍 Diagnóstico de Errores

### Error: "Connection refused"
- MySQL no está ejecutándose
- Puerto 3306 bloqueado
- Credenciales incorrectas

### Error: "Database not found"
- La base de datos no se creó
- Ejecuta el script de inicialización

### Error: "Invalid credentials"
- Verifica las credenciales
- La contraseña es: `admin123` (sin espacios)

### Error: "CORS error"
- El backend no está ejecutándose
- Verifica que esté en puerto 8080

## 🛠️ Solución Rápida

### Usar el Script Automático

1. **Ejecutar el script de configuración:**
   ```bash
   configurar_sistema.bat
   ```

2. **Seguir las instrucciones en pantalla**

3. **Abrir el frontend:**
   ```
   http://localhost/Ciempies_SGI/views/index.html
   ```

## 📱 Verificar Frontend

### 1. Abrir la página de login
```
http://localhost/Ciempies_SGI/views/index.html
```

### 2. Verificar en la consola del navegador (F12)
- No debe haber errores de CORS
- Las peticiones deben ir a `http://localhost:8080/api/`

### 3. Probar login
- Usuario: `admin@ciempies.com`
- Contraseña: `admin123`

## 🔧 Si el problema persiste

### 1. Verificar logs del backend
Busca en la consola donde ejecutaste `mvn spring-boot:run`:
- Errores de conexión a base de datos
- Errores de autenticación
- Errores de JWT

### 2. Verificar base de datos
```sql
mysql -u root -p
USE ciempies_sgi;
SELECT * FROM usuarios WHERE correo = 'admin@ciempies.com';
```

### 3. Verificar configuración de red
- Firewall bloqueando puerto 8080
- Antivirus interfiriendo
- Proxy configurado

## 📞 Información de Contacto

Si el problema persiste, verifica:

1. **Versión de Java:** `java -version` (debe ser 17+)
2. **Versión de Maven:** `mvn -version`
3. **Versión de MySQL:** `mysql --version`
4. **Estado de XAMPP:** MySQL y Apache ejecutándose

## ✅ Verificación Final

Para confirmar que todo funciona:

1. **Backend:** `http://localhost:8080/api/swagger-ui.html` ✅
2. **Frontend:** `http://localhost/Ciempies_SGI/views/index.html` ✅
3. **Login:** admin@ciempies.com / admin123 ✅
4. **Dashboard:** Debe mostrar estadísticas ✅

---

**¡Si sigues estos pasos, el login debería funcionar correctamente! 🎉**
