@echo off
echo ========================================
echo    CIEMPIÉS SGI - Sistema de Gestión
echo ========================================
echo.

echo Verificando Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo Por favor instala Java 17 o superior
    pause
    exit /b 1
)

echo.
echo Verificando Maven...
mvn -version
if %errorlevel% neq 0 (
    echo ERROR: Maven no está instalado o no está en el PATH
    echo Por favor instala Maven 3.6 o superior
    pause
    exit /b 1
)

echo.
echo Verificando que XAMPP esté corriendo...
echo Por favor asegúrate de que MySQL esté iniciado en XAMPP
echo.

echo Iniciando la aplicación CIEMPIÉS SGI...
echo.
echo La aplicación estará disponible en: http://localhost:8080
echo Credenciales de prueba:
echo   Email: marcela1@cienpies.co
echo   Contraseña: 1234
echo.
echo Presiona Ctrl+C para detener la aplicación
echo.

mvn spring-boot:run

pause
