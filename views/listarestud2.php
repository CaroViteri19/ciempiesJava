<?php
// Conexión a la base de datos
$mysqli = new mysqli("localhost", "root", "", "ciempies");

// Verificar conexión
if ($mysqli->connect_error) {
  die("Conexión fallida: " . $mysqli->connect_error);
}

// Consulta
$sql = "SELECT Id_Inscripcion, Cod_Ruta, Nombre_estudiante FROM Inscripcion";
$resultado = $mysqli->query($sql);

// Preparar datos
$datos = [];

if ($resultado && $resultado->num_rows > 0) {
  while ($row = $resultado->fetch_assoc()) {
    $datos[] = $row;
  }
}

// Respuesta en formato JSON
echo json_encode($datos, JSON_UNESCAPED_UNICODE);


$mysqli->close();
?>
