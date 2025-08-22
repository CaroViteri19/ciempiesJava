-- Script de inicialización para CIEMPIÉS SGI
-- Ejecutar después de crear la base de datos

USE ciempies;

-- Crear tabla de usuarios si no existe
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    rol ENUM('ADMINISTRADOR', 'APOYO_TERRITORIAL', 'MONITOR', 'COORDINADOR') NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso DATETIME NULL
);

-- Insertar usuarios iniciales
INSERT INTO usuarios (nombre, correo, contraseña, rol, activo) VALUES
('Administrador', 'admin@ciempies.gov.co', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMINISTRADOR', true),
('Apoyo Territorial', 'apoyo@ciempies.gov.co', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'APOYO_TERRITORIAL', true),
('Monitor Ruta 1', 'monitor1@ciempies.gov.co', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'MONITOR', true),
('Coordinador', 'coordinador@ciempies.gov.co', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'COORDINADOR', true);

-- Insertar rutas de ejemplo
INSERT INTO ruta (Cod_Ruta, Nombre_ruta, Descripcion, Capacidad_Maxima, Activa) VALUES
('RT001', 'Ruta Norte', 'Ruta que cubre el sector norte de la ciudad', 30, true),
('RT002', 'Ruta Sur', 'Ruta que cubre el sector sur de la ciudad', 25, true),
('RT003', 'Ruta Este', 'Ruta que cubre el sector este de la ciudad', 28, true),
('RT004', 'Ruta Oeste', 'Ruta que cubre el sector oeste de la ciudad', 32, true),
('RT005', 'Ruta Centro', 'Ruta que cubre el centro de la ciudad', 35, true),
('RT006', 'Ruta Nororiente', 'Ruta que cubre el sector nororiente', 27, true),
('RT007', 'Ruta Noroccidente', 'Ruta que cubre el sector noroccidente', 29, true),
('RT008', 'Ruta Suroriente', 'Ruta que cubre el sector suroriente', 26, true),
('RT009', 'Ruta Suroccidente', 'Ruta que cubre el sector suroccidente', 31, true);

-- Insertar estudiantes de ejemplo
INSERT INTO estudiante (Nombre, Apellido, Documento, Sexo, EPS, Direccion, Edad, Discapacidad, Etnia, Curso, Telefono, Id_Ruta, Fecha_Registro, Activo) VALUES
('Juan Carlos', 'Pérez', '1234567890', 'MASCULINO', 'EPS Sura', 'Calle 123 #45-67', 12, NULL, NULL, 'Sexto', '3001234567', 1, CURDATE(), true),
('María José', 'García', '2345678901', 'FEMENINO', 'EPS Famisanar', 'Carrera 78 #12-34', 10, NULL, NULL, 'Cuarto', '3002345678', 1, CURDATE(), true),
('Carlos Andrés', 'López', '3456789012', 'MASCULINO', 'EPS Compensar', 'Avenida 56 #78-90', 14, 'Auditiva', NULL, 'Octavo', '3003456789', 2, CURDATE(), true),
('Ana Sofía', 'Rodríguez', '4567890123', 'FEMENINO', 'EPS Sanitas', 'Calle 90 #12-34', 11, NULL, 'Indígena', 'Quinto', '3004567890', 2, CURDATE(), true),
('Luis Fernando', 'Martínez', '5678901234', 'MASCULINO', 'EPS Coomeva', 'Carrera 34 #56-78', 13, NULL, NULL, 'Séptimo', '3005678901', 3, CURDATE(), true),
('Valentina', 'Hernández', '6789012345', 'FEMENINO', 'EPS Nueva EPS', 'Avenida 12 #34-56', 9, NULL, NULL, 'Tercero', '3006789012', 3, CURDATE(), true),
('Diego Alejandro', 'González', '7890123456', 'MASCULINO', 'EPS Salud Total', 'Calle 67 #89-01', 15, NULL, 'Afrodescendiente', 'Noveno', '3007890123', 4, CURDATE(), true),
('Isabella', 'Díaz', '8901234567', 'FEMENINO', 'EPS Colsubsidio', 'Carrera 45 #67-89', 12, 'Visual', NULL, 'Sexto', '3008901234', 4, CURDATE(), true),
('Santiago', 'Morales', '9012345678', 'MASCULINO', 'EPS Medimás', 'Avenida 23 #45-67', 10, NULL, NULL, 'Cuarto', '3009012345', 5, CURDATE(), true),
('Camila', 'Rojas', '0123456789', 'FEMENINO', 'EPS Cafesalud', 'Calle 89 #01-23', 14, NULL, NULL, 'Octavo', '3010123456', 5, CURDATE(), true);

-- Insertar algunas asistencias de ejemplo
INSERT INTO asistencias (Cod_Ruta, Nombre_ruta, Nombre_Estudiante, Estado, Fecha, Hora_Inicio, Hora_Final, Observaciones, Id_asistencia) VALUES
(1, 'Ruta Norte', 'Juan Carlos Pérez', 'PRESENTE', CURDATE(), '07:00:00', '08:30:00', 'Llegó puntual', 1),
(1, 'Ruta Norte', 'María José García', 'PRESENTE', CURDATE(), '07:00:00', '08:30:00', 'Llegó puntual', 2),
(2, 'Ruta Sur', 'Carlos Andrés López', 'PRESENTE', CURDATE(), '07:15:00', '08:45:00', 'Llegó puntual', 3),
(2, 'Ruta Sur', 'Ana Sofía Rodríguez', 'AUSENTE', CURDATE(), '07:15:00', '08:45:00', 'No se presentó', 4),
(3, 'Ruta Este', 'Luis Fernando Martínez', 'PRESENTE', CURDATE(), '07:30:00', '09:00:00', 'Llegó puntual', 5);

-- Mensaje de confirmación
SELECT 'Base de datos CIEMPIÉS inicializada correctamente' AS mensaje;
SELECT COUNT(*) AS total_usuarios FROM usuarios;
SELECT COUNT(*) AS total_rutas FROM ruta;
SELECT COUNT(*) AS total_estudiantes FROM estudiante;
SELECT COUNT(*) AS total_asistencias FROM asistencias;
