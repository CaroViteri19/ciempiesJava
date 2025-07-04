<?php
require_once 'conexion.php';

session_start();

// Roles permitidos
$rol = $_SESSION['rol'] ?? '';
$roles_autorizados = ['administrador', 'apoyo territorial'];

if (!in_array($rol, $roles_autorizados)) {
    echo "<script>
            alert('⛔ Acceso denegado para el rol: $rol');
            window.location.href = 'modulos.php';
          </script>";
    exit();
}

// Mensaje de bienvenida

echo "<script>alert('✅ Bienvenido, rol: $rol');</script>";


require_once 'conexion.php';

if (isset($_POST['eliminar'])) {
    $documento = $mysqli->real_escape_string($_POST['documento']);
    $sql = "DELETE FROM inscripcion WHERE documento = '$documento'";
    if ($mysqli->query($sql)) {
        $mensaje = "✅ Inscripción eliminada correctamente.";
    } else {
        $mensaje = "❌ Error al eliminar: " . $mysqli->error;
    }
}


$mensaje = "";
$inscripcion = null;


if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Buscar inscripción por documento
    if (isset($_POST['buscar'])) {
        $documento = $mysqli->real_escape_string($_POST['documento']);
        $sql = "SELECT * FROM inscripcion WHERE documento = '$documento'";
        $resultado = $mysqli->query($sql);
        $inscripcion = $resultado->fetch_assoc();

        if (!$inscripcion) {
            $mensaje = "❌ No se encontró inscripción con el documento $documento.";
        }
    }

    // Registrar nueva inscripción
    if (isset($_POST['registrar'])) {
        $nombre = $mysqli->real_escape_string($_POST['nombre']);
        $documento = $mysqli->real_escape_string($_POST['documento']);
        $zona = intval($_POST['cod_zona']);
        $fecha = date("Y-m-d");
        $estado = $mysqli->real_escape_string($_POST['estado']);
        

        $sql = "INSERT INTO inscripcion (`Cod_Zona`, `Nombre estudiante`, `documento`, `Fecha_Inscripcion`, `Estado`)
                VALUES ($zona, '$nombre', '$documento', '$fecha', '$estado')";

        if ($mysqli->query($sql)) {
            $mensaje = "✅ Inscripción registrada correctamente.";
        } else {
            $mensaje = "❌ Error al registrar: " . $mysqli->error;
        }
    }

    // Actualizar estado
    if (isset($_POST['actualizar'])) {
        $id_inscripcion = intval($_POST['id_inscripcion']);
        $estado = $mysqli->real_escape_string($_POST['estado']);
        $sql = "UPDATE inscripcion SET Estado = '$estado' WHERE Id_Inscripcion = $id_inscripcion";
        if ($mysqli->query($sql)) {
            $mensaje = "✅ Estado actualizado correctamente.";
        } else {
            $mensaje = "❌ Error al actualizar: " . $mysqli->error;
        }
    }
}
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inscripción Estudiante</title>
    <script src="https://cdn.tailwindcss.com"></script>
     <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
  <link rel="stylesheet" href="style.css" />
  <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gradient-to-b from-pink-100 to-blue-100 min-h-screen p-4">
<!-- Logo y botón juntos -->
<div class="fixed top-4 left-4 z-50 flex items-center space-x-4">
  <!-- Logo -->
  <img src="logo.jpeg" alt="Logo" class="h-20 w-20 rounded-full object-cover shadow-md">

  <!-- Botón de regreso -->
  <a href="modulos.php" class="flex items-center space-x-2 bg-pink-100 text-white font-semibold px-4 py-2 rounded-full shadow hover:bg-cyan-600 transition transform hover:scale-105">
    <img src="pag_inicio.png" alt="Inicio" class="h-5">
    <span>volver</span>
  </a>
</div>

  <div class="flex items-center justify-center min-h-screen">
    <div class="bg-white shadow-2xl rounded-lg p-8 w-full max-w-3xl mt-20">
      <h2 class="text-2xl font-bold text-purple-700 text-center mb-6">Formulario de Inscripción</h2>
        <!-- Formulario de registro -->
        <form method="post" class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8">
            <input type="text" name="nombre" required placeholder="Nombre estudiante" class="col-span-1 px-4 py-2 border rounded">
            <input type="text" name="documento" required placeholder="Documento" class="col-span-1 px-4 py-2 border rounded">
            <input type="number" name="cod_zona" required placeholder="Código de zona" class="col-span-1 px-4 py-2 border rounded">
            <select name="estado" required class="col-span-1 px-4 py-2 border rounded">
                <option value="">Selecciona estado</option>
                <option value="activo">Activo</option>
                <option value="pendiente">Pendiente</option>
                <option value="rechazado">Rechazado</option>
            </select>
            <button type="submit" name="registrar" class="col-span-2 bg-purple-600 text-white py-2 rounded hover:bg-purple-700">Registrar Inscripción</button>
        </form>

        <!-- Formulario de búsqueda -->
        <form method="post" class="mb-6 flex gap-2">
            <input type="text" name="documento" placeholder="Buscar por documento" required class="flex-1 px-4 py-2 border rounded">
            <button type="submit" name="buscar" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Buscar</button>
        </form>
        <form method="post" onsubmit="return confirm('¿Estás seguro de eliminar esta inscripción?');">
    <input type="hidden" name="documento" value="<?= htmlspecialchars($inscripcion['documento']) ?>">
    <button type="submit" name="eliminar" class="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700">Eliminar inscripción</button>
</form>


        <!-- Resultados de búsqueda -->
        <?php if ($inscripcion): ?>
            <div class="bg-purple-50 p-4 rounded mb-4 text-sm">
                <p><strong>Nombre:</strong> <?= htmlspecialchars($inscripcion['Nombre estudiante']) ?></p>
                <p><strong>Documento:</strong> <?= htmlspecialchars($inscripcion['documento']) ?></p>
                <p><strong>Zona:</strong> <?= htmlspecialchars($inscripcion['Cod_Zona']) ?></p>
                <p><strong>Fecha:</strong> <?= htmlspecialchars($inscripcion['Fecha_Inscripcion']) ?></p>
                <p><strong>Estado actual:</strong> <?= htmlspecialchars($inscripcion['Estado']) ?></p>
            </div>

            <form method="post" onsubmit="return confirm('¿Actualizar estado?');" class="space-y-4">
                <input type="hidden" name="id_inscripcion" value="<?= $inscripcion['Id_Inscripcion'] ?>">
                <select name="estado" required class="w-full px-4 py-2 border rounded">
                    <option value="">Selecciona nuevo estado</option>
                    <option value="activo">Activo</option>
                    <option value="pendiente">Pendiente</option>
                    <option value="rechazado">Rechazado</option>
                </select>
                <button type="submit" name="actualizar" class="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700">Actualizar Estado</button>
            </form>
        <?php endif; ?>

        <?php if ($mensaje): ?>
            <div class="mt-6 p-4 rounded bg-blue-100 text-blue-800 text-center font-semibold"><?= $mensaje ?></div>
        <?php endif; ?>
    </div>
</body>
</html>
