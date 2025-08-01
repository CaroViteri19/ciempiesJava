<?php
// Conexión a la base de datos
$mysqli = new mysqli("localhost", "root", "", "ciempies");

// Verificar conexión
if ($mysqli->connect_error) {
  die("Conexión fallida: " . $mysqli->connect_error);
}

// Consulta para obtener los datos
$sql = "SELECT nombre, apellido, documento, sexo, eps, direccion, edad, discapacidad, etnia, curso, telefono FROM estudiante ORDER BY Id_estudiante DESC LIMIT 15";

$resultado = $mysqli->query($sql);

// Generar las filas HTML
if ($resultado && $resultado->num_rows > 0) {
  while ($row = $resultado->fetch_assoc()) {
    echo "<tr>
      <td class='border px-2 py-1'>{$row['nombre']}</td>
      <td class='border px-2 py-1'>{$row['apellido']}</td>
      <td class='border px-2 py-1'>{$row['documento']}</td>
      <td class='border px-2 py-1'>{$row['sexo']}</td>
      <td class='border px-2 py-1'>{$row['eps']}</td>
      <td class='border px-2 py-1'>{$row['direccion']}</td>
      <td class='border px-2 py-1'>{$row['edad']}</td>
      <td class='border px-2 py-1'>{$row['discapacidad']}</td>
      <td class='border px-2 py-1'>{$row['etnia']}</td>
      <td class='border px-2 py-1'>{$row['curso']}</td>
      <td class='border px-2 py-1'>{$row['telefono']}</td>
    </tr>";
  }
} else {
  echo "<tr><td colspan='11' class='text-center py-2'>No hay estudiantes registrados</td></tr>";
}

$mysqli->close();
?>
