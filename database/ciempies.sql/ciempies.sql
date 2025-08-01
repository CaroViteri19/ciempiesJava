-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-08-2025 a las 02:29:26
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ciempies`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `asignar_estudiantes_a_rutas` ()   BEGIN
    -- Todas las declaraciones deben ir primero
    DECLARE done INT DEFAULT FALSE;
    DECLARE estudiante_id INT;
    DECLARE ruta_aleatoria INT;
    DECLARE total_estudiantes INT;
    DECLARE total_rutas INT;
    DECLARE estudiantes_por_ruta INT;
    DECLARE contador INT DEFAULT 0;
    DECLARE cur CURSOR FOR SELECT Id_Estudiante FROM estudiante ORDER BY RAND();
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- Contar estudiantes y rutas
    SELECT COUNT(*) INTO total_estudiantes FROM estudiante;
    SELECT COUNT(*) INTO total_rutas FROM ruta WHERE FIND_IN_SET(Id_Ruta, @rutas_disponibles);
    
    -- Calcular distribución aproximada
    SET estudiantes_por_ruta = CEIL(total_estudiantes / total_rutas);
    
    OPEN cur;
    
    read_loop: LOOP
        FETCH cur INTO estudiante_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        -- Seleccionar ruta aleatoria de las disponibles
        SET ruta_aleatoria = (
            SELECT Id_Ruta FROM ruta 
            WHERE FIND_IN_SET(Id_Ruta, @rutas_disponibles)
            ORDER BY RAND() 
            LIMIT 1
        );
        
        -- Asignar estudiante a ruta
        UPDATE estudiante SET Id_Ruta = ruta_aleatoria WHERE Id_Estudiante = estudiante_id;
        UPDATE inscripcion SET Id_ruta = ruta_aleatoria WHERE Id_Estudiante = estudiante_id;
        
        SET contador = contador + 1;
        
        -- Rotar rutas para distribución más equitativa
        IF contador % estudiantes_por_ruta = 0 THEN
            SET @rutas_disponibles = (
                SELECT GROUP_CONCAT(Id_Ruta) 
                FROM ruta 
                WHERE FIND_IN_SET(Id_Ruta, @rutas_disponibles)
                ORDER BY RAND()
            );
        END IF;
    END LOOP;
    
    CLOSE cur;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencias`
--

CREATE TABLE `asistencias` (
  `Cod_Ruta` int(11) NOT NULL,
  `Nombre_ruta` varchar(50) DEFAULT NULL,
  `Nombre_Estudiante` varchar(50) DEFAULT NULL,
  `Estado` enum('presente','ausente') NOT NULL,
  `Fecha` date NOT NULL,
  `Hora_Inicio` time NOT NULL,
  `Hora_Final` time NOT NULL,
  `Observaciones` text DEFAULT NULL,
  `Id_asistencia` int(11) NOT NULL,
  `Id_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `asistencias`
--

INSERT INTO `asistencias` (`Cod_Ruta`, `Nombre_ruta`, `Nombre_Estudiante`, `Estado`, `Fecha`, `Hora_Inicio`, `Hora_Final`, `Observaciones`, `Id_asistencia`, `Id_Usuario`) VALUES
(1, NULL, NULL, 'presente', '2023-10-03', '07:00:00', '08:00:00', 'Llegó puntual', 1, 1),
(2, NULL, NULL, 'ausente', '2023-10-03', '07:30:00', '08:30:00', 'No asistió', 2, 2),
(3, NULL, NULL, 'presente', '2023-10-04', '07:00:00', '08:00:00', 'Llegó puntual', 3, 3),
(4, NULL, NULL, 'ausente', '2023-10-04', '07:30:00', '08:30:00', 'No asistió', 4, 4),
(5, NULL, NULL, 'presente', '2023-10-05', '07:00:00', '08:00:00', 'Llegó puntual', 5, 5),
(6, NULL, NULL, 'ausente', '2023-10-05', '07:30:00', '08:30:00', 'No asistió', 6, 6),
(7, NULL, NULL, 'presente', '2023-10-06', '07:00:00', '08:00:00', 'Llegó puntual', 7, 7),
(8, NULL, NULL, 'ausente', '2023-10-06', '07:30:00', '08:30:00', 'No asistió', 8, 8),
(9, NULL, NULL, 'presente', '2023-10-07', '07:00:00', '08:00:00', 'Llegó puntual', 9, 9),
(1, NULL, NULL, 'presente', '2023-10-10', '07:15:00', '08:15:00', NULL, 10, 10),
(2, NULL, NULL, 'presente', '2023-10-11', '07:20:00', '08:20:00', NULL, 11, 11),
(3, NULL, NULL, 'presente', '2023-10-12', '07:25:00', '08:25:00', NULL, 12, 12),
(4, NULL, NULL, 'presente', '2023-10-13', '07:30:00', '08:30:00', NULL, 13, 13),
(5, NULL, NULL, 'presente', '2023-10-14', '07:35:00', '08:35:00', NULL, 14, 14),
(6, NULL, NULL, 'ausente', '2023-10-15', '07:40:00', '08:40:00', NULL, 15, 15),
(7, NULL, NULL, 'presente', '2023-10-16', '07:45:00', '08:45:00', NULL, 16, 16),
(8, NULL, NULL, 'ausente', '2023-10-17', '07:50:00', '08:50:00', NULL, 17, 17),
(9, NULL, NULL, 'ausente', '2023-10-18', '07:55:00', '08:55:00', NULL, 18, 18),
(1, NULL, NULL, 'ausente', '2023-10-19', '08:00:00', '09:00:00', NULL, 19, 19),
(2, NULL, NULL, 'presente', '2023-10-20', '08:05:00', '09:05:00', NULL, 20, 10),
(105, 'Ruta Norte', 'Nicol', 'presente', '2025-07-03', '12:00:00', '02:00:00', 'na', 21, 5),
(105, 'Ruta Centro', 'santiago', 'presente', '2025-07-03', '10:00:00', '02:00:00', 'na', 22, 5),
(105, 'Ruta Norte', 'Manuela', 'presente', '2025-07-03', '12:00:00', '12:00:00', 'na', 23, 8),
(105, 'Ruta Norte', 'Manuela', 'presente', '2025-07-03', '12:00:00', '12:00:00', 'na', 24, 8),
(101, 'Ruta Norte', 'herson', 'presente', '2025-07-03', '12:00:00', '02:00:00', 'na', 25, 8),
(101, 'Ruta Centro', 'zajuna', 'presente', '2025-07-03', '12:00:00', '02:00:00', 'na', 26, 8),
(104, 'Ruta Centro', 'Daniela Rios', 'ausente', '2025-07-03', '11:00:00', '01:10:00', 'na', 27, 8),
(105, 'Ruta Norte', 'santiago', 'ausente', '2025-07-03', '10:00:00', '02:00:00', 'na', 28, 3),
(104, 'Ruta Norte', 'Olga Martinez', 'ausente', '2025-07-03', '09:00:00', '02:00:00', 'na', 29, 5),
(101, 'Ruta Centro', 'Elizabeth', 'presente', '2025-07-03', '11:00:00', '01:10:00', 'na', 30, 1),
(109, 'Ruta Norte', 'paula', 'ausente', '2025-07-03', '02:00:00', '05:00:00', 'na', 31, 2),
(101, 'Ruta Norte', 'pee', 'presente', '2025-07-04', '11:00:00', '02:00:00', 'na', 32, 5),
(104, 'Ruta Norte', 'kevin', 'ausente', '2025-07-05', '03:00:00', '05:00:00', 'na', 33, 1),
(101, 'Ruta Norte', 'Esteban galindo', 'presente', '2025-07-05', '01:00:00', '03:00:00', 'na', 34, 6),
(106, 'Ruta Norte', 'fernando villegas', 'ausente', '2025-07-06', '12:00:00', '13:00:00', 'na', 35, 5),
(102, 'Ruta Sur', 'fernandito villegas', 'presente', '2025-07-06', '12:00:00', '13:00:00', 'sin novedad', 36, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudiante`
--

CREATE TABLE `estudiante` (
  `Id_Estudiante` int(11) NOT NULL,
  `Id_acudiente` int(11) NOT NULL,
  `Id_colegio` int(11) NOT NULL,
  `Id_Jornada` int(11) NOT NULL,
  `Id_Ruta` int(11) NOT NULL,
  `Nombre` varchar(15) NOT NULL,
  `Apellido` varchar(15) NOT NULL,
  `Documento` int(11) NOT NULL,
  `Sexo` enum('femenino','masculino') NOT NULL,
  `Eps` varchar(15) DEFAULT NULL,
  `Direccion` varchar(25) NOT NULL,
  `Edad` tinyint(4) NOT NULL,
  `Discapacidad` varchar(15) DEFAULT NULL,
  `Etnia` varchar(15) DEFAULT NULL,
  `Fecha_Inscripcion` date NOT NULL,
  `Curso` varchar(10) NOT NULL,
  `Telefono` varchar(50) DEFAULT NULL,
  `Id_Inscripcion` int(11) DEFAULT NULL,
  `Id_Asistencia` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estudiante`
--

INSERT INTO `estudiante` (`Id_Estudiante`, `Id_acudiente`, `Id_colegio`, `Id_Jornada`, `Id_Ruta`, `Nombre`, `Apellido`, `Documento`, `Sexo`, `Eps`, `Direccion`, `Edad`, `Discapacidad`, `Etnia`, `Fecha_Inscripcion`, `Curso`, `Telefono`, `Id_Inscripcion`, `Id_Asistencia`) VALUES
(1, 1, 1, 1, 6, 'Juan', 'Pérez', 1234567890, 'masculino', 'Sanitas', 'Calle 123', 10, NULL, NULL, '2023-10-01', '5A', '127', 1, 1),
(2, 2, 2, 2, 7, 'María', 'Gómez', 1987654321, 'femenino', 'Sura', 'Carrera 456', 9, NULL, NULL, '2023-10-02', '4B', '127', 2, 2),
(3, 3, 3, 1, 5, 'Carlos', 'López', 1112223333, 'masculino', 'Coomeva', 'Calle 789', 11, NULL, NULL, '2023-10-03', '6C', '127', 3, 3),
(4, 4, 4, 2, 1, 'Ana', 'Martínez', 1511222333, 'femenino', 'Nueva EPS', 'Carrera 101', 8, NULL, NULL, '2023-10-04', '3D', '127', 4, 4),
(5, 5, 5, 1, 2, 'Pedro', 'García', 1555666777, 'masculino', 'Salud Total', 'Calle 112', 12, NULL, NULL, '2023-10-05', '7E', '127', 5, 5),
(6, 6, 6, 2, 3, 'Laura', 'Rodríguez', 1888999000, 'femenino', 'Sanitas', 'Carrera 131', 10, NULL, NULL, '2023-10-06', '5F', '127', 6, 6),
(7, 7, 7, 1, 9, 'Sofía', 'Hernández', 1222333444, 'femenino', 'Sura', 'Calle 415', 9, NULL, NULL, '2023-10-07', '4G', '127', 7, 7),
(8, 8, 8, 2, 7, 'Diego', 'Díaz', 1777888999, 'masculino', 'Coomeva', 'Carrera 161', 11, NULL, NULL, '2023-10-08', '6H', '127', 8, 8),
(9, 9, 9, 1, 9, 'Lucía', 'Pérez', 1444555666, 'femenino', 'Nueva EPS', 'Calle 718', 8, NULL, NULL, '2023-10-09', '3I', '127', 9, 9),
(10, 10, 10, 2, 6, 'Mateo', 'Gómez', 1333444555, 'masculino', 'Sura', 'Calle 100', 11, NULL, NULL, '2023-10-10', '5B', '127', 10, 10),
(11, 11, 11, 1, 3, 'Valentina', 'López', 1888999222, 'femenino', 'Coomeva', 'Carrera 200', 10, NULL, NULL, '2023-10-11', '4C', '127', 11, 11),
(12, 12, 12, 2, 5, 'Samuel', 'Martínez', 1222333111, 'masculino', 'Nueva EPS', 'Calle 300', 9, NULL, NULL, '2023-10-12', '3D', '127', 12, 12),
(13, 13, 13, 1, 8, 'Isabella', 'García', 1777888333, 'femenino', 'Sanitas', 'Carrera 400', 12, NULL, NULL, '2023-10-13', '6E', '127', 13, 13),
(14, 14, 14, 2, 7, 'Daniel', 'Rodríguez', 1555666999, 'masculino', 'Sura', 'Calle 500', 8, NULL, NULL, '2023-10-14', '2F', '127', 14, 14),
(15, 15, 15, 1, 8, 'Sara', 'Hernández', 1999111222, 'femenino', 'Coomeva', 'Carrera 600', 11, NULL, NULL, '2023-10-15', '5G', '127', 15, 15),
(16, 16, 16, 2, 8, 'David', 'Díaz', 1111333444, 'masculino', 'Nueva EPS', 'Calle 700', 10, NULL, NULL, '2023-10-16', '4H', '127', 16, 16),
(17, 17, 17, 1, 9, 'Emma', 'Pérez', 1444777888, 'femenino', 'Sanitas', 'Carrera 800', 9, NULL, NULL, '2023-10-17', '3I', '127', 17, 17),
(18, 18, 18, 2, 3, 'Sebastián', 'Sánchez', 1666999111, 'masculino', 'Sura', 'Calle 900', 12, NULL, NULL, '2023-10-18', '6J', '127', 18, 18),
(19, 19, 19, 1, 5, 'Victoria', 'Ramírez', 1987654321, 'femenino', 'Coomeva', 'Carrera 1000', 8, NULL, NULL, '2023-10-19', '2K', '127', 19, 19),
(20, 20, 20, 2, 4, 'Nicolás', 'Torres', 1234567890, 'masculino', 'Nueva EPS', 'Calle 1100', 11, NULL, NULL, '2023-10-20', '5L', '127', 20, 20),
(70, 0, 0, 0, 0, 'caroliina', 'lopez', 12345, 'femenino', 'compensar', 'CALLE 67C 105 27', 4, 'no', 'no', '0000-00-00', '3a', '127', NULL, NULL),
(71, 0, 0, 0, 0, 'herson ', 'pinzon', 123455, 'masculino', 'famisanar', 'cra 104 #13d', 12, 'auditiva', 'indigena', '0000-00-00', '7a', '127', NULL, NULL),
(72, 0, 0, 0, 0, 'Javier', 'Lopez', 3287283, 'masculino', 'famisanar', 'cra 104 #13d', 13, 'auditiva', 'n/a', '0000-00-00', '5c', '32146578', NULL, NULL),
(73, 0, 0, 0, 0, 'fernandito ', 'villegas', 142423523, 'masculino', 'famisanar', 'cra 104 #13d-9', 13, 'na', 'na', '0000-00-00', '6a', '32146578', NULL, NULL),
(74, 0, 0, 0, 0, 'marcela', 'ramirez', 123585154, 'femenino', 'compensar', 'cra 13 - 54 - 56 ', 10, 'ninguna', 'ninguna', '0000-00-00', '201', '20124455', NULL, NULL),
(75, 0, 0, 0, 0, 'Yeinner', 'Ramirez', 1043443277, 'masculino', 'Salud Total', 'calle 2334455', 17, 'NA', 'Negro', '0000-00-00', '6a', '12345677', NULL, NULL),
(95, 0, 0, 0, 0, 'James', 'Rodriguez', 14323352, 'masculino', 'compensar', 'cra 13 - 54 - 56 ', 12, 'auditiva', 'n/a', '2025-07-08', '6a', '12764625417', NULL, NULL),
(96, 0, 0, 0, 0, 'Santiago', ' Rios Cajamarca', 1019783519, 'masculino', 'sanitas', 'cra 104 #13d', 15, 'na', 'n/a', '2025-07-08', '6a', '32146578', NULL, NULL),
(97, 0, 0, 0, 0, 'Luis', 'Rios', 31452736, 'masculino', 'famisanar', 'calle 2334455', 13, 'na', 'n/a', '2025-07-08', '5c', '1224235466', NULL, NULL),
(98, 0, 0, 0, 0, 'Jennifer ', 'gonzales', 132111111, 'masculino', 'compensar', 'calle 2334455', 12, 'auditiva', 'n/a', '2025-07-11', '7a', '12764625417', NULL, NULL),
(99, 0, 0, 0, 0, 'pablo', 'vargas', 122222, 'masculino', 'sanitas', 'calle 2 b 3 1', 6, 'no', 'no', '2025-08-01', '2a', '12345678', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inscripcion`
--

CREATE TABLE `inscripcion` (
  `Id_Inscripcion` int(11) NOT NULL,
  `Cod_Ruta` int(11) NOT NULL,
  `Nombre_estudiante` varchar(100) NOT NULL,
  `documento` varchar(50) NOT NULL,
  `Fecha_Inscripcion` date NOT NULL,
  `Estado` enum('activo','pendiente','rechazado') NOT NULL,
  `Id_ruta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inscripcion`
--

INSERT INTO `inscripcion` (`Id_Inscripcion`, `Cod_Ruta`, `Nombre_estudiante`, `documento`, `Fecha_Inscripcion`, `Estado`, `Id_ruta`) VALUES
(1, 101, '1', 'Inscripción exitosa', '2023-10-01', 'pendiente', NULL),
(2, 102, '2', 'Inscripción pendiente', '2023-10-02', 'rechazado', NULL),
(3, 103, '3', 'Inscripción exitosa', '2023-10-03', 'rechazado', NULL),
(4, 104, '4', 'Inscripción pendiente', '2023-10-04', 'activo', NULL),
(5, 105, '5', 'Inscripción exitosa', '2023-10-05', 'activo', NULL),
(6, 106, '6', 'Inscripción pendiente', '2023-10-06', 'pendiente', NULL),
(7, 107, '7', 'Inscripción exitosa', '2023-10-07', 'activo', NULL),
(8, 108, '8', 'Inscripción pendiente', '2023-10-08', 'activo', NULL),
(9, 109, '9', 'Inscripción exitosa', '2023-10-09', 'pendiente', NULL),
(10, 101, '10', 'Inscripción exitosa', '2023-10-10', 'activo', NULL),
(11, 102, '11', 'Inscripción pendiente', '2023-10-11', 'activo', NULL),
(12, 103, '12', 'Inscripción exitosa', '2023-10-12', 'activo', NULL),
(13, 104, '13', 'Inscripción pendiente', '2023-10-13', 'pendiente', NULL),
(14, 105, '14', 'Inscripción exitosa', '2023-10-14', 'activo', NULL),
(15, 106, '15', 'Inscripción pendiente', '2023-10-15', 'activo', NULL),
(16, 107, '16', 'Inscripción exitosa', '2023-10-16', 'activo', NULL),
(17, 108, '17', 'Inscripción pendiente', '2023-10-17', 'activo', NULL),
(18, 109, '18', 'Inscripción exitosa', '2023-10-18', 'activo', NULL),
(19, 110, '19', 'Inscripción pendiente', '2023-10-19', 'activo', NULL),
(20, 101, '3', 'Inscripción exitosa', '2023-10-10', 'rechazado', NULL),
(21, 102, '2', 'Inscripción pendiente', '2023-10-11', 'rechazado', NULL),
(22, 103, '1', 'Inscripción exitosa', '2023-10-12', 'pendiente', NULL),
(23, 104, '4', 'Inscripción pendiente', '2023-10-13', 'activo', NULL),
(24, 105, '6', 'Inscripción exitosa', '2023-10-14', 'pendiente', NULL),
(25, 106, '5', 'Inscripción pendiente', '2023-10-15', 'activo', NULL),
(26, 107, '7', 'Inscripción exitosa', '2023-10-16', 'activo', NULL),
(27, 108, '8', 'Inscripción pendiente', '2023-10-17', 'activo', NULL),
(28, 109, '9', 'Inscripción exitosa', '2023-10-18', 'pendiente', NULL),
(29, 110, '10', 'Inscripción pendiente', '2023-10-19', 'activo', NULL),
(32, 102, 'carola', '1234', '2025-07-02', 'activo', NULL),
(47, 1573, 'fernandito villegas', '142423523', '2025-07-06', 'activo', NULL),
(48, 104, 'James Rodriguez', '14323352', '2025-07-08', 'pendiente', NULL),
(49, 109, 'Santiago  Rios Cajamarca', '1019783519', '2025-07-08', 'pendiente', NULL),
(50, 106, 'Luis Rios', '31452736', '2025-07-08', 'pendiente', NULL),
(51, 105, 'Jennifer  gonzales', '132111111', '2025-07-11', 'activo', NULL),
(52, 102, 'Luis', '	31452736', '2025-08-01', 'pendiente', NULL),
(53, 102, 'pablo', '122222', '2025-08-01', 'activo', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta`
--

CREATE TABLE `ruta` (
  `Id_Ruta` int(11) NOT NULL,
  `Id_Estudiante` int(11) NOT NULL,
  `Id_Usuario` int(11) NOT NULL,
  `Cod_Ruta` int(11) NOT NULL,
  `Id_Inscripcion` int(11) DEFAULT NULL,
  `Nombre_ruta` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ruta`
--

INSERT INTO `ruta` (`Id_Ruta`, `Id_Estudiante`, `Id_Usuario`, `Cod_Ruta`, `Id_Inscripcion`, `Nombre_ruta`) VALUES
(1, 1, 1, 101, NULL, 'Ruta Norte'),
(2, 2, 2, 102, NULL, 'Ruta Sur'),
(3, 3, 3, 103, NULL, 'Ruta Este'),
(4, 4, 4, 104, NULL, 'Ruta Oeste'),
(5, 5, 5, 105, NULL, 'Ruta Centro'),
(6, 6, 6, 106, NULL, 'Ruta Nororiente'),
(7, 7, 7, 107, NULL, 'Ruta Noroccidente'),
(8, 8, 8, 108, NULL, 'Ruta Suroriente'),
(9, 9, 9, 109, NULL, 'Ruta Suroriente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Id_Usuario` int(11) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `Apellido` varchar(20) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Contraseña` varchar(15) NOT NULL,
  `Rol` varchar(50) NOT NULL,
  `Id_Zona` int(11) DEFAULT NULL,
  `Id_ruta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`Id_Usuario`, `Nombre`, `Apellido`, `Email`, `Contraseña`, `Rol`, `Id_Zona`, `Id_ruta`) VALUES
(1, 'Marcela', 'Rios', 'marcela1@cienpies.co', '1234', 'apoyo territorial', 1, 1),
(2, 'Carlos', 'Gómez', 'carlos@cienpies.co', '5678', 'monitor', 2, NULL),
(3, 'Ana', 'López', 'ana@cienpies.co', '91011', 'administrador', 3, NULL),
(4, 'Pedro', 'Martínez', 'pedro@cienpies.co', '1213', 'apoyo territorial', 4, NULL),
(5, 'Laura', 'García', 'laura@cienpies.co', '1415', 'monitor', 5, NULL),
(6, 'Juan', 'Rodríguez', 'juan@cienpies.co', '1617', 'administrador', 6, NULL),
(7, 'Sofía', 'Hernández', 'sofia@cienpies.co', '1819', 'apoyo territorial', 7, NULL),
(8, 'Diego', 'Díaz', 'diego@cienpies.co', '2021', 'monitor', 8, NULL),
(9, 'Lucía', 'Pérez', 'lucia@cienpies.co', '2223', 'administrador', 9, NULL),
(10, 'Miguel', 'Sánchez', 'miguel@cienpies.co', '2425', 'apoyo territorial', 10, NULL),
(11, 'Marcela', 'Rios', 'marcela1@cienpies.com', '1234', 'apoyo territorial', NULL, NULL),
(12, 'Laura', 'González', 'laura.gonzalez@cienpies.co', 'pass123', 'apoyo territorial', 1, 1),
(13, 'Carlos', 'Martínez', 'carlos.martinez@cienpies.co', 'pass456', 'monitor', 2, NULL),
(14, 'Sofía', 'López', 'sofia.lopez@cienpies.co', 'pass789', 'administrador', 3, NULL),
(15, 'Pedro', 'Sánchez', 'pedro.sanchez@cienpies.co', 'pass101', 'apoyo territorial', 4, 2),
(16, 'Ana', 'Ramírez', 'ana.ramirez@cienpies.co', 'pass112', 'monitor', 5, NULL),
(17, 'Juan', 'Torres', 'juan.torres@cienpies.co', 'pass131', 'administrador', 6, NULL),
(18, 'María', 'Díaz', 'maria.diaz@cienpies.co', 'pass415', 'apoyo territorial', 7, 3),
(19, 'Luis', 'Hernández', 'luis.hernandez@cienpies.co', 'pass161', 'monitor', 8, NULL),
(20, 'Lucía', 'Flores', 'lucia.flores@cienpies.co', 'pass718', 'administrador', 9, NULL),
(21, 'Diego', 'Vargas', 'diego.vargas@cienpies.co', 'pass192', 'apoyo territorial', 10, 4),
(22, 'Elena', 'Castro', 'elena.castro@cienpies.co', 'pass202', 'monitor', NULL, NULL),
(23, 'Andrés', 'Rojas', 'andres.rojas@cienpies.co', 'pass212', 'administrador', NULL, NULL),
(24, 'Camila', 'Mendoza', 'camila.mendoza@cienpies.co', 'pass223', 'apoyo territorial', 1, 5),
(25, 'Javier', 'Silva', 'javier.silva@cienpies.co', 'pass234', 'monitor', 2, NULL),
(26, 'Valeria', 'Peña', 'valeria.pena@cienpies.co', 'pass245', 'administrador', 3, NULL),
(27, 'Ricardo', 'Ortiz', 'ricardo.ortiz@cienpies.co', 'pass256', 'supervisor', 4, 6),
(28, 'Fernanda', 'Guerrero', 'fernanda.guerrero@cienpies.co', 'pass267', 'acompañante', 5, NULL),
(29, 'Gabriel', 'Navarro', 'gabriel.navarro@cienpies.co', 'pass278', 'administrador', 6, NULL),
(30, 'Daniela', 'Molina', 'daniela.molina@cienpies.co', 'pass289', 'supervisor', 7, 7),
(31, 'Roberto', 'Cortés', 'roberto.cortes@cienpies.co', 'pass290', 'acompañante', 8, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zona`
--

CREATE TABLE `zona` (
  `Id_Zona` int(11) NOT NULL,
  `Id_Colegio` int(11) NOT NULL,
  `Cod_Zona` int(11) NOT NULL,
  `Id_Usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `zona`
--

INSERT INTO `zona` (`Id_Zona`, `Id_Colegio`, `Cod_Zona`, `Id_Usuario`) VALUES
(1, 1, 101, 1),
(2, 2, 102, 1),
(3, 3, 103, 2),
(4, 4, 104, 4),
(5, 5, 105, 5),
(6, 6, 106, 6),
(7, 7, 107, 7),
(8, 8, 108, 8),
(9, 9, 109, 9),
(10, 10, 110, 10);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asistencias`
--
ALTER TABLE `asistencias`
  ADD PRIMARY KEY (`Id_asistencia`),
  ADD KEY `fk_Id_Usuario_` (`Id_Usuario`);

--
-- Indices de la tabla `estudiante`
--
ALTER TABLE `estudiante`
  ADD PRIMARY KEY (`Id_Estudiante`),
  ADD KEY `fk_Id_Inscripcion` (`Id_Inscripcion`);

--
-- Indices de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD PRIMARY KEY (`Id_Inscripcion`),
  ADD KEY `fk_Id_ruta` (`Id_ruta`);

--
-- Indices de la tabla `ruta`
--
ALTER TABLE `ruta`
  ADD PRIMARY KEY (`Id_Ruta`),
  ADD KEY `fk_Id_Usuario` (`Id_Usuario`),
  ADD KEY `fk_Id_Inscripcion` (`Id_Inscripcion`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Id_Usuario`),
  ADD KEY `fk_id_Zona` (`Id_Zona`),
  ADD KEY `fk_id_ruta` (`Id_ruta`);

--
-- Indices de la tabla `zona`
--
ALTER TABLE `zona`
  ADD PRIMARY KEY (`Id_Zona`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asistencias`
--
ALTER TABLE `asistencias`
  MODIFY `Id_asistencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT de la tabla `estudiante`
--
ALTER TABLE `estudiante`
  MODIFY `Id_Estudiante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100;

--
-- AUTO_INCREMENT de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  MODIFY `Id_Inscripcion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT de la tabla `ruta`
--
ALTER TABLE `ruta`
  MODIFY `Id_Ruta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `Id_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `zona`
--
ALTER TABLE `zona`
  MODIFY `Id_Zona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asistencias`
--
ALTER TABLE `asistencias`
  ADD CONSTRAINT `fk_Id_Usuario_` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`);

--
-- Filtros para la tabla `estudiante`
--
ALTER TABLE `estudiante`
  ADD CONSTRAINT `fk_Id_Inscripcion` FOREIGN KEY (`Id_Inscripcion`) REFERENCES `inscripcion` (`Id_Inscripcion`);

--
-- Filtros para la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD CONSTRAINT `fk_Id_ruta` FOREIGN KEY (`Id_ruta`) REFERENCES `ruta` (`Id_Ruta`);

--
-- Filtros para la tabla `ruta`
--
ALTER TABLE `ruta`
  ADD CONSTRAINT `fk_Id_Usuario` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_id_Zona` FOREIGN KEY (`Id_Zona`) REFERENCES `zona` (`Id_Zona`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
