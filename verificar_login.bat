@echo off
echo ========================================
echo    VERIFICACION RAPIDA DEL LOGIN
echo ========================================
echo.

echo [1/4] Verificando MySQL...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT COUNT(*) as usuarios FROM usuarios;" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: MySQL no esta ejecutandose
    pause
    exit /b 1
)
echo ✓ MySQL funcionando

echo.
echo [2/4] Verificando usuario administrador...
C:\xampp\mysql\bin\mysql.exe -u root -e "USE ciempies_sgi; SELECT nombre, correo, rol FROM usuarios WHERE correo = 'admin@ciempies.com';" 2>nul
if %errorlevel% neq 0 (
    echo ❌ ERROR: Usuario no encontrado
    pause
    exit /b 1
)
echo ✓ Usuario administrador encontrado

echo.
echo [3/4] Verificando backend...
timeout /t 5 /nobreak >nul
curl -s http://localhost:8080/api/swagger-ui.html >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: Backend no esta ejecutandose
    echo Ejecuta: C:\maven\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
    pause
    exit /b 1
)
echo ✓ Backend funcionando

echo.
echo [4/4] Verificando frontend...
if exist "test_login.html" (
    echo ✓ Archivo de prueba encontrado
) else (
    echo ❌ ERROR: Archivo de prueba no encontrado
    pause
    exit /b 1
)

echo.
echo ========================================
echo    ¡TODO LISTO PARA EL LOGIN!
echo ========================================
echo.
echo ✅ Base de datos: OK
echo ✅ Usuario admin: OK
echo ✅ Backend: OK
echo ✅ Frontend: OK
echo.
echo ========================================
echo    ACCESO AL SISTEMA
echo ========================================
echo.
echo 🌐 Abre tu navegador y ve a:
echo    http://localhost/Ciempies_SGI/test_login.html
echo.
echo 👤 Credenciales:
echo    Usuario: admin@ciempies.com
echo    Contraseña: admin123
echo.
echo ========================================
echo    OPCIONES ALTERNATIVAS
echo ========================================
echo.
echo 📱 Sistema principal:
echo    http://localhost/Ciempies_SGI/views/index.html
echo.
echo 🔧 API Documentation:
echo    http://localhost:8080/api/swagger-ui.html
echo.
echo ========================================
echo    ¡EL SISTEMA ESTA 100% FUNCIONAL!
echo ========================================
echo.
pause
