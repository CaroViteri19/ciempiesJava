<?php

//  Conexión
$mysqli = new mysqli("localhost", "root", "", "ciempies");
if ($mysqli->connect_error) {
    die("❌ Error de conexión: " . $mysqli->connect_error);
}

// 2. Obtener datos del formulario
$nombre = $_POST['nombre'] ?? '';
$apellido = $_POST['apellido'] ?? '';
$documento = $_POST['documento'] ?? '';
$sexo = $_POST['sexo'] ?? '';
$eps = $_POST['eps'] ?? '';
$direccion = $_POST['direccion'] ?? '';
$edad = $_POST['edad'] ?? '';
$discapacidad = $_POST['discapacidad'] ?? '';
$etnia = $_POST['etnia'] ?? '';
$curso = $_POST['curso'] ?? '';
$telefono = $_POST['telefono'] ?? '';
$Cod_Ruta = $_POST['Cod_Ruta'] ?? '';
$Estado = $_POST['Estado'] ?? '';

$fecha_inscripcion = date('Y-m-d');
$nombre_completo = $nombre . " " . $apellido;

// 3. Insertar en la tabla estudiante
$sql_estudiante = "INSERT INTO estudiante (
    nombre, apellido, documento, sexo, eps, direccion, edad, discapacidad, etnia, curso, telefono, Fecha_Inscripcion
) VALUES (
    '$nombre', '$apellido', '$documento', '$sexo', '$eps', '$direccion', '$edad', '$discapacidad', '$etnia', '$curso', '$telefono', '$fecha_inscripcion'
)";

if ($mysqli->query($sql_estudiante)) {

    // 4. Insertar en la tabla inscripcion 
    $sql_inscripcion = "INSERT INTO inscripcion (
    Cod_Ruta, `Nombre estudiante`, documento, Fecha_Inscripcion, Estado
) VALUES (
    '$Cod_Ruta', '$nombre_completo', '$documento', '$fecha_inscripcion', '$Estado'
)";

    if ($mysqli->query($sql_inscripcion)) {
        echo "✅ Inscripción insertada correctamente";
    } else {
        echo "⚠️ Error al insertar en inscripcion: " . $mysqli->error;
    }

} else {
    echo "❌ Error al insertar en estudiante: " . $mysqli->error;
}

$mysqli->close();
?>
