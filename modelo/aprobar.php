<?php
include 'conexion.php'; // incluye la conexión

// Recibir los datos del formulario
$nombre = $_POST['nombre'] ?? '';
$apellido = $_POST['apellido'] ?? '';
$documento = $_POST['documento'] ?? '';
$correo = $_POST['correo'] ?? '';
$fecha_nacimiento = $_POST['fecha_nacimiento'] ?? '';
$genero = $_POST['genero'] ?? '';
$direccion = $_POST['direccion'] ?? '';
$curso = $_POST['curso'] ?? '';
$escuela = $_POST['escuela'] ?? '';
$nombre_acudiente = $_POST['nombre_acudiente'] ?? '';
$documento_acudiente = $_POST['documento_acudiente'] ?? '';
$observaciones = $_POST['observaciones'] ?? '';

// Supongamos que ya tienes un ID de estudiante generado o lo obtendrás de alguna forma
// Aquí deberías primero insertar en la tabla de estudiante y luego en inscripción si están relacionadas

// Inserción en tabla "estudiante" (ajusta los campos reales)
$sql = "INSERT INTO estudiante (nombre, apellido, documento, correo, fecha_nacimiento, genero, direccion, curso, escuela, nombre_acudiente, documento_acudiente, observaciones)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

$stmt = $conexion->prepare($sql);
$stmt->bind_param("ssssssssssss", $nombre, $apellido, $documento, $correo, $fecha_nacimiento, $genero, $direccion, $curso, $escuela, $nombre_acudiente, $documento_acudiente, $observaciones);

if ($stmt->execute()) {
    echo "<script>
            alert('✅ Registro exitoso');
            window.location.href = 'formulario.html';
          </script>";
} else {
    echo "<script>
            alert('❌ Error al registrar: " . $stmt->error . "');
            window.history.back();
          </script>";
}

$stmt->close();
$conexion->close();
?>
