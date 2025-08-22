@echo off
echo ========================================
echo    DIAGNOSTICO PROBLEMA DE LOGIN
echo ========================================
echo.

echo [1/6] Verificando MySQL...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT COUNT(*) as usuarios FROM usuarios;" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: MySQL no esta ejecutandose
    echo Por favor inicia XAMPP y ejecuta MySQL
    pause
    exit /b 1
)
echo ✓ MySQL funcionando

echo.
echo [2/6] Verificando usuario administrador...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT id, nombre, correo, rol, activo FROM usuarios WHERE correo = 'admin@ciempies.com';" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: No se pudo verificar el usuario
    pause
    exit /b 1
)
echo ✓ Usuario administrador encontrado

echo.
echo [3/6] Verificando contraseña encriptada...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT contrasena FROM usuarios WHERE correo = 'admin@ciempies.com';" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: No se pudo verificar la contraseña
    pause
    exit /b 1
)
echo ✓ Contraseña encriptada correcta

echo.
echo [4/6] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: Java no esta instalado
    pause
    exit /b 1
)
echo ✓ Java encontrado

echo.
echo [5/6] Verificando Maven...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: Maven no esta instalado
    echo Ejecuta: instalar_maven.bat
    pause
    exit /b 1
)
echo ✓ Maven encontrado

echo.
echo [6/6] Verificando servidor backend...
timeout /t 5 /nobreak >nul
curl -s http://localhost:8080/api/swagger-ui.html >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: El servidor backend no esta ejecutandose
    echo Ejecuta: mvn spring-boot:run
    echo.
    echo Esperando 10 segundos para que inicie...
    timeout /t 10 /nobreak >nul
    curl -s http://localhost:8080/api/swagger-ui.html >nul 2>&1
    if %errorlevel% neq 0 (
        echo ❌ ERROR: El servidor sigue sin responder
        pause
        exit /b 1
    )
)
echo ✓ Servidor backend funcionando

echo.
echo ========================================
echo    DIAGNOSTICO COMPLETADO
echo ========================================
echo.
echo ✅ Todo parece estar funcionando correctamente
echo.
echo ========================================
echo    PRUEBA DE LOGIN
echo ========================================
echo.
echo Abre tu navegador y ve a:
echo http://localhost/Ciempies_SGI/test_login.html
echo.
echo O prueba directamente en:
echo http://localhost/Ciempies_SGI/views/index.html
echo.
echo Credenciales:
echo 👤 Usuario: admin@ciempies.com
echo 🔑 Contraseña: admin123
echo.
echo ========================================
echo    SOLUCIONES SI NO FUNCIONA
echo ========================================
echo.
echo 1. Si el backend no inicia:
echo    - Verifica que el puerto 8080 esté libre
echo    - Ejecuta: netstat -ano | findstr :8080
echo.
echo 2. Si hay error de CORS:
echo    - Verifica que el backend esté en puerto 8080
echo    - Verifica que el frontend esté en localhost
echo.
echo 3. Si las credenciales no funcionan:
echo    - Ejecuta: Get-Content database/setup_database.sql ^| C:\xampp\mysql\bin\mysql.exe -u root
echo.
echo 4. Si hay error de base de datos:
echo    - Verifica que XAMPP esté ejecutándose
echo    - Verifica que MySQL esté activo
echo.
pause
