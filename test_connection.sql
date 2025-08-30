-- Script de prueba para verificar la conexión a la base de datos CIEMPIÉS
-- Ejecutar este script para verificar que todo esté funcionando correctamente

USE ciempies;

-- Verificar que las tablas principales existan
SELECT 'Verificando tablas principales...' AS mensaje;

-- Verificar tabla usuario
SELECT COUNT(*) AS total_usuarios FROM usuario;

-- Verificar tabla estudiante
SELECT COUNT(*) AS total_estudiantes FROM estudiante;

-- Verificar tabla asistencias
SELECT COUNT(*) AS total_asistencias FROM asistencias;

-- Verificar tabla ruta
SELECT COUNT(*) AS total_rutas FROM ruta;

-- Verificar tabla inscripcion
SELECT COUNT(*) AS total_inscripciones FROM inscripcion;

-- Verificar tabla zona
SELECT COUNT(*) AS total_zonas FROM zona;

-- Mostrar algunos usuarios de prueba
SELECT 'Usuarios disponibles para login:' AS mensaje;
SELECT Id_Usuario, Nombre, Apellido, Email, Contraseña, Rol 
FROM usuario 
LIMIT 5;

-- Verificar que el usuario de prueba existe
SELECT 'Usuario de prueba para login:' AS mensaje;
SELECT Id_Usuario, Nombre, Apellido, Email, Contraseña, Rol 
FROM usuario 
WHERE Email = 'marcela1@cienpies.co';

-- Mostrar estadísticas generales
SELECT 'Estadísticas del sistema:' AS mensaje;
SELECT 
    (SELECT COUNT(*) FROM usuario) AS total_usuarios,
    (SELECT COUNT(*) FROM estudiante) AS total_estudiantes,
    (SELECT COUNT(*) FROM asistencias) AS total_asistencias,
    (SELECT COUNT(*) FROM ruta) AS total_rutas,
    (SELECT COUNT(*) FROM inscripcion) AS total_inscripciones,
    (SELECT COUNT(*) FROM zona) AS total_zonas;
