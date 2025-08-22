@echo off
echo ========================================
echo    DIAGNOSTICO COMPLETO DEL SISTEMA
echo ========================================
echo.

echo [1/8] Verificando XAMPP y MySQL...
C:\xampp\mysql\bin\mysql.exe -u root -e "SELECT VERSION();" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: XAMPP/MySQL no esta ejecutandose
    echo Por favor inicia XAMPP y ejecuta MySQL
    pause
    exit /b 1
)
echo ✓ XAMPP y MySQL funcionando

echo.
echo [2/8] Verificando base de datos ciempies_sgi...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT COUNT(*) as tablas FROM information_schema.tables WHERE table_schema = 'ciempies_sgi';" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: Base de datos ciempies_sgi no existe
    echo Ejecutando script de inicialización...
    Get-Content database/setup_database.sql | C:\xampp\mysql\bin\mysql.exe -u root
    if %errorlevel% neq 0 (
        echo ❌ ERROR: No se pudo crear la base de datos
        pause
        exit /b 1
    )
    echo ✓ Base de datos creada
) else (
    echo ✓ Base de datos ciempies_sgi existe
)

echo.
echo [3/8] Verificando usuario administrador...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT id, nombre, correo, rol, activo FROM usuarios WHERE correo = 'admin@ciempies.com';" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: No se pudo verificar el usuario administrador
    pause
    exit /b 1
)
echo ✓ Usuario administrador encontrado

echo.
echo [4/8] Verificando contraseña encriptada...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT contrasena FROM usuarios WHERE correo = 'admin@ciempies.com';" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: No se pudo verificar la contraseña
    pause
    exit /b 1
)
echo ✓ Contraseña encriptada correcta

echo.
echo [5/8] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: Java no esta instalado
    pause
    exit /b 1
)
echo ✓ Java encontrado

echo.
echo [6/8] Verificando Maven...
C:\maven\apache-maven-3.9.6\bin\mvn.cmd -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: Maven no esta instalado
    echo Ejecuta: instalar_maven.bat
    pause
    exit /b 1
)
echo ✓ Maven encontrado

echo.
echo [7/8] Compilando proyecto...
C:\maven\apache-maven-3.9.6\bin\mvn.cmd clean compile >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: No se pudo compilar el proyecto
    pause
    exit /b 1
)
echo ✓ Proyecto compilado correctamente

echo.
echo [8/8] Verificando puerto 8080...
netstat -ano | findstr :8080 >nul 2>&1
if %errorlevel% equ 0 (
    echo ⚠️  ADVERTENCIA: Puerto 8080 ya está en uso
    echo Verificando si es el backend...
    timeout /t 3 /nobreak >nul
    curl -s http://localhost:8080/api/swagger-ui.html >nul 2>&1
    if %errorlevel% equ 0 (
        echo ✓ Backend ya está ejecutándose
    ) else (
        echo ❌ Puerto 8080 ocupado por otro proceso
        echo Ejecuta: netstat -ano | findstr :8080
        echo Luego mata el proceso con: taskkill /PID [PID] /F
    )
) else (
    echo ✓ Puerto 8080 libre
)

echo.
echo ========================================
echo    DIAGNOSTICO COMPLETADO
echo ========================================
echo.
echo ✅ Base de datos: OK
echo ✅ Usuario admin: OK  
echo ✅ Java: OK
echo ✅ Maven: OK
echo ✅ Compilación: OK
echo.
echo ========================================
echo    INICIANDO BACKEND
echo ========================================
echo.
echo Ejecutando: C:\maven\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
echo.
echo Espera unos 30 segundos para que inicie completamente...
echo Luego abre: http://localhost/Ciempies_SGI/test_login.html
echo.
echo Credenciales:
echo 👤 Usuario: admin@ciempies.com
echo 🔑 Contraseña: admin123
echo.
pause

C:\maven\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
