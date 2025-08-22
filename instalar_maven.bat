@echo off
echo ========================================
echo    INSTALACION DE MAVEN
echo ========================================
echo.

echo [1/4] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java no esta instalado
    echo Por favor instala Java 17 o superior
    pause
    exit /b 1
)
echo ✓ Java encontrado

echo.
echo [2/4] Creando directorio para Maven...
if not exist "C:\maven" mkdir "C:\maven"
echo ✓ Directorio creado

echo.
echo [3/4] Descargando Maven...
echo Descargando Maven 3.9.6...
powershell -Command "& {Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip' -OutFile 'C:\maven\maven.zip'}"
if %errorlevel% neq 0 (
    echo ERROR: No se pudo descargar Maven
    echo Verifica tu conexion a internet
    pause
    exit /b 1
)
echo ✓ Maven descargado

echo.
echo [4/4] Extrayendo Maven...
powershell -Command "& {Expand-Archive -Path 'C:\maven\maven.zip' -DestinationPath 'C:\maven' -Force}"
if %errorlevel% neq 0 (
    echo ERROR: No se pudo extraer Maven
    pause
    exit /b 1
)
echo ✓ Maven extraido

echo.
echo Configurando variables de entorno...
setx MAVEN_HOME "C:\maven\apache-maven-3.9.6" /M
setx PATH "%PATH%;C:\maven\apache-maven-3.9.6\bin" /M

echo.
echo ========================================
echo    MAVEN INSTALADO CORRECTAMENTE
echo ========================================
echo.
echo Por favor reinicia tu terminal y ejecuta:
echo mvn -version
echo.
echo Luego ejecuta:
echo configurar_sistema.bat
echo.
pause
