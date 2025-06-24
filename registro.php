<?php
$mysqli = new mysqli("localhost", "root", "", "ciempies");
if ($mysqli->connect_error) {
    die("Conexión fallida: " . $mysqli->connect_error);
}

$nombre = $_POST['nombre'];
$apellido = $_POST['apellido'];
$documento = $_POST['documento'];
$sexo = $_POST['sexo'];
$eps = $_POST['eps'];
$direccion = $_POST['direccion'];
$edad = $_POST['edad'];
$discapacidad = $_POST['discapacidad'];
$etnia = $_POST['etnia'];
$curso = $_POST['curso'];
$telefono = $_POST['telefono'];

$sql = "INSERT INTO estudiante (nombre, apellido, documento, sexo, eps, direccion, edad, discapacidad, etnia, curso, telefono)
VALUES ('$nombre', '$apellido', '$documento', '$sexo', '$eps', '$direccion', '$edad', '$discapacidad', '$etnia', '$curso', '$telefono')";

if ($mysqli->query($sql)) {
    header("Location: formulario.html"); // redirige a la página del formulario
    exit;
} else {
    echo "Error: " . $mysqli->error;
}

$mysqli->close();
?>
