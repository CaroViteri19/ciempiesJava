-- Script de inicialización para CIEMPIÉS SGI
-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS ciempies_sgi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ciempies_sgi;

-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'COORDINADOR', 'MONITOR') NOT NULL DEFAULT 'MONITOR',
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP NULL
);

-- Crear tabla de rutas
CREATE TABLE IF NOT EXISTS ruta (
    id_ruta BIGINT AUTO_INCREMENT PRIMARY KEY,
    cod_ruta VARCHAR(20) UNIQUE NOT NULL,
    nombre_ruta VARCHAR(100) NOT NULL,
    descripcion TEXT,
    capacidad_maxima INT NOT NULL DEFAULT 25,
    activa BOOLEAN DEFAULT TRUE
);

-- Crear tabla de estudiantes
CREATE TABLE IF NOT EXISTS estudiante (
    id_estudiante BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    documento VARCHAR(20) UNIQUE NOT NULL,
    sexo ENUM('MASCULINO', 'FEMENINO') NOT NULL,
    eps VARCHAR(100),
    direccion TEXT,
    edad INT,
    discapacidad BOOLEAN DEFAULT FALSE,
    etnia VARCHAR(50),
    curso INT NOT NULL,
    telefono VARCHAR(20),
    id_ruta BIGINT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_ruta) REFERENCES ruta(id_ruta)
);

-- Crear tabla de asistencias
CREATE TABLE IF NOT EXISTS asistencias (
    id_asistencia BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_ruta BIGINT,
    nombre_ruta VARCHAR(100),
    id_estudiante BIGINT,
    nombre_estudiante VARCHAR(100),
    estado ENUM('PRESENTE', 'AUSENTE', 'TARDANZA') NOT NULL,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_final TIME NOT NULL,
    observaciones TEXT,
    id_usuario_registro BIGINT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_ruta) REFERENCES ruta(id_ruta),
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante),
    FOREIGN KEY (id_usuario_registro) REFERENCES usuarios(id)
);

-- Insertar usuario administrador por defecto
-- Contraseña: admin123 (encriptada con BCrypt)
INSERT INTO usuarios (nombre, correo, contraseña, rol, activo) VALUES 
('Administrador', 'admin@ciempies.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'ADMIN', TRUE)
ON DUPLICATE KEY UPDATE 
    nombre = VALUES(nombre),
    contraseña = VALUES(contraseña),
    rol = VALUES(rol),
    activo = VALUES(activo);

-- Insertar algunas rutas de ejemplo
INSERT INTO ruta (cod_ruta, nombre_ruta, descripcion, capacidad_maxima, activa) VALUES 
('R001', 'Ruta Norte', 'Ruta que cubre la zona norte de la ciudad', 25, TRUE),
('R002', 'Ruta Sur', 'Ruta que cubre la zona sur de la ciudad', 30, TRUE),
('R003', 'Ruta Este', 'Ruta que cubre la zona este de la ciudad', 20, TRUE),
('R004', 'Ruta Oeste', 'Ruta que cubre la zona oeste de la ciudad', 25, TRUE)
ON DUPLICATE KEY UPDATE 
    nombre_ruta = VALUES(nombre_ruta),
    descripcion = VALUES(descripcion),
    capacidad_maxima = VALUES(capacidad_maxima),
    activa = VALUES(activa);

-- Insertar algunos estudiantes de ejemplo
INSERT INTO estudiante (nombre, apellido, documento, sexo, eps, direccion, edad, curso, telefono, id_ruta, activo) VALUES 
('Juan', 'Pérez', '1234567890', 'MASCULINO', 'EPS Sura', 'Calle 123 #45-67', 15, 10, '3001234567', 1, TRUE),
('María', 'García', '0987654321', 'FEMENINO', 'EPS Famisanar', 'Carrera 78 #90-12', 16, 11, '3009876543', 1, TRUE),
('Carlos', 'López', '1122334455', 'MASCULINO', 'EPS Compensar', 'Avenida 5 #23-45', 14, 9, '3001122334', 2, TRUE),
('Ana', 'Martínez', '5566778899', 'FEMENINO', 'EPS Sanitas', 'Calle 89 #12-34', 17, 11, '3005566778', 2, TRUE),
('Luis', 'Rodríguez', '9988776655', 'MASCULINO', 'EPS Sura', 'Carrera 12 #34-56', 15, 10, '3009988776', 3, TRUE)
ON DUPLICATE KEY UPDATE 
    nombre = VALUES(nombre),
    apellido = VALUES(apellido),
    sexo = VALUES(sexo),
    eps = VALUES(eps),
    direccion = VALUES(direccion),
    edad = VALUES(edad),
    curso = VALUES(curso),
    telefono = VALUES(telefono),
    id_ruta = VALUES(id_ruta),
    activo = VALUES(activo);

-- Insertar algunas asistencias de ejemplo para hoy
INSERT INTO asistencias (id_ruta, nombre_ruta, id_estudiante, nombre_estudiante, estado, fecha, hora_inicio, hora_final, observaciones, id_usuario_registro) VALUES 
(1, 'Ruta Norte', 1, 'Juan Pérez', 'PRESENTE', CURDATE(), '06:30:00', '07:00:00', 'Llegó a tiempo', 1),
(1, 'Ruta Norte', 2, 'María García', 'PRESENTE', CURDATE(), '06:35:00', '07:05:00', 'Llegó a tiempo', 1),
(2, 'Ruta Sur', 3, 'Carlos López', 'TARDANZA', CURDATE(), '06:45:00', '07:15:00', 'Llegó 15 minutos tarde', 1),
(2, 'Ruta Sur', 4, 'Ana Martínez', 'PRESENTE', CURDATE(), '06:30:00', '07:00:00', 'Llegó a tiempo', 1),
(3, 'Ruta Este', 5, 'Luis Rodríguez', 'AUSENTE', CURDATE(), '06:30:00', '07:00:00', 'No se presentó', 1);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_usuarios_correo ON usuarios(correo);
CREATE INDEX idx_usuarios_activo ON usuarios(activo);
CREATE INDEX idx_estudiante_documento ON estudiante(documento);
CREATE INDEX idx_estudiante_activo ON estudiante(activo);
CREATE INDEX idx_estudiante_ruta ON estudiante(id_ruta);
CREATE INDEX idx_ruta_codigo ON ruta(cod_ruta);
CREATE INDEX idx_ruta_activa ON ruta(activa);
CREATE INDEX idx_asistencias_fecha ON asistencias(fecha);
CREATE INDEX idx_asistencias_ruta ON asistencias(id_ruta);
CREATE INDEX idx_asistencias_estudiante ON asistencias(id_estudiante);

-- Mostrar mensaje de confirmación
SELECT 'Base de datos CIEMPIÉS SGI inicializada correctamente' AS mensaje;
SELECT 'Usuario administrador creado: admin@ciempies.com / admin123' AS credenciales;
