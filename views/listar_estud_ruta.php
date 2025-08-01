<?php
// Conexión a la base de datos
$mysqli = new mysqli("localhost", "root", "", "ciempies");

// Verificar conexión
if ($mysqli->connect_error) {
  die("Conexión fallida: " . $mysqli->connect_error);
}

// Consulta
$sql = "SELECT Id_Estudiante, nombre, apellido, curso, Id_Ruta FROM estudiante";
$resultado = $mysqli->query($sql);

// Preparar datos
$estudiantes = [];

if ($resultado && $resultado->num_rows > 0) {
  while ($row = $resultado->fetch_assoc()) {
    $estudiantes[] = $row;
  }
}

// Respuesta en formato JSON
header('Content-Type: application/json');
echo json_encode($estudiantes);

$mysqli->close();
?>
