@echo off
echo ========================================
echo    VERIFICACION DEL SISTEMA
echo ========================================
echo.

echo [1/5] Verificando MySQL...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT COUNT(*) as usuarios FROM usuarios;" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: MySQL no esta ejecutandose o la base de datos no existe
    echo Por favor inicia XAMPP y ejecuta el script de base de datos
    pause
    exit /b 1
)
echo ✓ MySQL y base de datos funcionando

echo.
echo [2/5] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: Java no esta instalado
    pause
    exit /b 1
)
echo ✓ Java encontrado

echo.
echo [3/5] Verificando Maven...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: Maven no esta instalado
    echo Ejecuta: instalar_maven.bat
    pause
    exit /b 1
)
echo ✓ Maven encontrado

echo.
echo [4/5] Verificando servidor backend...
timeout /t 3 /nobreak >nul
curl -s http://localhost:8080/api/swagger-ui.html >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: El servidor backend no esta ejecutandose
    echo Ejecuta: mvn spring-boot:run
    pause
    exit /b 1
)
echo ✓ Servidor backend funcionando

echo.
echo [5/5] Verificando frontend...
if exist "views\index.html" (
    echo ✓ Archivos frontend encontrados
) else (
    echo ❌ ERROR: Archivos frontend no encontrados
    pause
    exit /b 1
)

echo.
echo ========================================
echo    SISTEMA LISTO PARA USAR
echo ========================================
echo.
echo ✅ Backend: http://localhost:8080/api/swagger-ui.html
echo ✅ Frontend: http://localhost/Ciempies_SGI/views/index.html
echo.
echo ========================================
echo    CREDENCIALES DE ACCESO
echo ========================================
echo 👤 Usuario: admin@ciempies.com
echo 🔑 Contraseña: admin123
echo ========================================
echo.
echo Para abrir el sistema:
echo 1. Abre tu navegador
echo 2. Ve a: http://localhost/Ciempies_SGI/views/index.html
echo 3. Ingresa las credenciales
echo.
pause
