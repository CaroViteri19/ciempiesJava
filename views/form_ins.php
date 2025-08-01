<?php
require_once('../modelo/conexion.php');
session_start();

// Verificar rol
$rol = $_SESSION['rol'] ?? '';
$roles_autorizados = ['administrador', 'apoyo territorial'];
if (!in_array($rol, $roles_autorizados)) {
    echo "<script>alert('⛔ Acceso denegado para el rol: $rol'); window.location.href = 'modulos.php';</script>";
    exit();
}

// Mostrar alerta de bienvenida una sola vez
if (!isset($_SESSION['bienvenida_mostrada'])) {
    echo "<script>alert('✅ Bienvenido, rol: $rol');</script>";
    $_SESSION['bienvenida_mostrada'] = true;
}

$mensaje = "";
$inscripcion = null;

// Procesar formulario
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Buscar inscripción por documento
    if (isset($_POST['buscar'])) {
        $documento = $mysqli->real_escape_string($_POST['documento']);

        $sql_est = "SELECT * FROM estudiante WHERE Documento = '$documento'";
        $res_est = $mysqli->query($sql_est);

        if ($res_est && $res_est->num_rows > 0) {
            $estudiante = $res_est->fetch_assoc();
            $nombre = $estudiante['Nombre'];

            $sql_ins = "SELECT * FROM inscripcion WHERE documento = '$documento'";
            $res_ins = $mysqli->query($sql_ins);

            if ($res_ins && $res_ins->num_rows > 0) {
                $inscripcion = $res_ins->fetch_assoc();
                $inscripcion['Nombre'] = $nombre;
            } else {
                $mensaje = "⚠️ El estudiante $nombre existe pero no está inscrito.";
            }
        } else {
            $mensaje = "❌ No se encontró ningún estudiante con el documento $documento.";
        }
    }

    // Registrar inscripción para estudiante existente
    if (isset($_POST['registrar'])) {
        $documento = $mysqli->real_escape_string($_POST['documento']);
        $cod_ruta = intval($_POST['Cod_Ruta']);
        $estado = $mysqli->real_escape_string($_POST['estado']);
        $fecha = date("Y-m-d");

        $sql_est = "SELECT Id_Estudiante, Nombre FROM estudiante WHERE Documento = '$documento'";
        $res_est = $mysqli->query($sql_est);

        if ($res_est && $res_est->num_rows > 0) {
            $estudiante = $res_est->fetch_assoc();
            $nombre_est = $mysqli->real_escape_string($estudiante['Nombre']);

            $sql_ins = "INSERT INTO inscripcion (Nombre_estudiante, Cod_Ruta, Fecha_Inscripcion, Estado, documento)
                        VALUES ('$nombre_est', $cod_ruta, '$fecha', '$estado', '$documento')";
            $mensaje = $mysqli->query($sql_ins)
                ? "✅ Estudiante inscrito correctamente a la ruta."
                : "❌ Error al registrar inscripción: " . $mysqli->error;
        } else {
            $mensaje = "❌ Estudiante no encontrado con documento $documento.";
        }
    }

    // Actualizar estado de inscripción
    if (isset($_POST['actualizar'])) {
        $id_inscripcion = intval($_POST['id_inscripcion']);
        $estado = $mysqli->real_escape_string($_POST['estado']);
        $sql = "UPDATE inscripcion SET Estado = '$estado' WHERE Id_Inscripcion = $id_inscripcion";
        $mensaje = $mysqli->query($sql)
            ? "✅ Estado actualizado correctamente."
            : "❌ Error al actualizar: " . $mysqli->error;
    }
}
?>

<!-- Mensaje flotante de bienvenida -->
<?php if (isset($_SESSION['bienvenida_mostrada']) && $_SESSION['bienvenida_mostrada']): ?>
    <div class="fixed top-4 right-4 bg-purple-200 text-purple-800 px-4 py-2 rounded-lg shadow-md font-semibold">
        Bienvenido <?php echo htmlspecialchars($rol); ?>
    </div>
<?php endif; 
echo "<script>
        alert('✅ Bienvenido: $rol');
      </script>";
?>

<div class="fixed top-4 right-4 bg-purple-200 text-purple-800 px-4 py-2 rounded-lg shadow-md font-semibold">
  Bienvenido <?php echo htmlspecialchars($rol); ?>
</div>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inscripción Estudiante</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="style.css">
</head>
<body class="bg-gradient-to-b from-pink-100 to-blue-100 min-h-screen p-4">
    <!-- Logo y botón de volver -->
    <div class="fixed top-4 left-4 z-50 flex items-center space-x-4">
        <img src="../assets/logo.jpeg" alt="Logo" class="h-20 w-20 rounded-full object-cover shadow-md">
        <a href="../controlador/modulos.php" 
            class="flex items-center space-x-2 bg-pink-100 text-white font-semibold px-4 py-2 rounded-full shadow hover:bg-cyan-600 transition transform hover:scale-105">
            <img src="../assets/click.png" alt="Inicio" class="h-5">
            Volver
        </a>
    </div>

    <div class="flex items-center justify-center min-h-screen pt-28">
        <div class="bg-white shadow-2xl rounded-lg p-8 w-full max-w-3xl">
            <h2 class="text-2xl font-bold text-purple-700 text-center mb-6">Formulario de Inscripción</h2>

            <!-- Formulario de registro -->
            <form method="post" class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8">
                <input type="text" name="documento" required placeholder="Documento del estudiante" class="col-span-1 px-4 py-2 border rounded">
                <select name="Cod_Ruta" required class="col-span-1 px-4 py-2 border rounded">
                    <option value="">Selecciona Ruta</option>
                    <?php for ($i = 101; $i <= 109; $i++): ?>
                        <option value="<?= $i ?>">Ruta <?= $i ?></option>
                    <?php endfor; ?>
                </select>
                <select name="estado" required class="col-span-2 px-4 py-2 border rounded">
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

            <!-- Resultados y actualización -->
            <?php if ($inscripcion): ?>
                <div class="bg-purple-50 p-4 rounded mb-4 text-sm">
                    <p><strong>Nombre:</strong> <?= htmlspecialchars($inscripcion['Nombre'] ?? 'Desconocido') ?></p>
                    <p><strong>Documento:</strong> <?= htmlspecialchars($documento ?? '') ?></p>
                    <p><strong>Código de Ruta:</strong> <?= htmlspecialchars($inscripcion['Cod_Ruta']) ?></p>
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
                <div class="mt-6 p-4 rounded bg-blue-100 text-blue-800 text-center font-semibold"> <?= $mensaje ?> </div>
            <?php endif; ?>
        </div>
    </div>
</body>
</html>


