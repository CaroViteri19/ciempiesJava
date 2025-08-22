@echo off
echo ========================================
echo    CONFIGURACION CIEMPIES SGI
echo ========================================
echo.

echo [1/4] Verificando MySQL...
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: MySQL no esta instalado o no esta en el PATH
    echo Por favor instala XAMPP o MySQL
    pause
    exit /b 1
)
echo ✓ MySQL encontrado

echo.
echo [2/4] Creando base de datos...
mysql -u root -p < database/ciempies.sql
if %errorlevel% neq 0 (
    echo ERROR: No se pudo crear la base de datos
    echo Verifica que MySQL este ejecutandose y las credenciales sean correctas
    pause
    exit /b 1
)
echo ✓ Base de datos creada

echo.
echo [3/4] Compilando proyecto Java...
mvn clean compile
if %errorlevel% neq 0 (
    echo ERROR: No se pudo compilar el proyecto
    echo Verifica que Java y Maven esten instalados
    pause
    exit /b 1
)
echo ✓ Proyecto compilado

echo.
echo [4/4] Iniciando servidor...
echo El servidor se iniciara en http://localhost:8080
echo Para detener el servidor presiona Ctrl+C
echo.
echo ========================================
echo    CREDENCIALES DE ACCESO
echo ========================================
echo Usuario: admin@ciempies.com
echo Contraseña: admin123
echo ========================================
echo.

mvn spring-boot:run

pause
