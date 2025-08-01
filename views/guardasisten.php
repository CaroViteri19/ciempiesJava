
<?php
session_start();
header('Content-Type: application/json');

    

// Verificar autenticación
if (!isset($_SESSION['id_usuario'])) {
    echo json_encode(['success' => false, 'message' => 'Usuario no autenticado']);
    exit;
}

// Configuración de conexión
$mysqli = new mysqli("localhost", "root", "", "ciempies");
if ($mysqli->connect_error) {
    echo json_encode(['success' => false, 'message' => 'Error de conexión a la BD']);
    exit;
}

// Procesar solo métodos POST
if ($_SERVER["REQUEST_METHOD"] != "POST") {
    echo json_encode(['success' => false, 'message' => 'Método no permitido']);
    exit;
}

try {
    // Obtener datos del formulario
    $nombres = $_POST['Nombre'] ?? [];
    $apellidos = $_POST['Apellido'] ?? [];
    $cod_rutas = $_POST['Cod_Ruta'] ?? [];
    $nombre_ruta = $_POST['nombre_ruta'] ?? ''; // Nuevo campo de nombre de ruta
    $estados = $_POST['Estado'] ?? [];
    $fechas = $_POST['Fecha'] ?? [];
    $horas_inicio = $_POST['Hora_Inicio'] ?? [];
    $horas_final = $_POST['Hora_Final'] ?? [];
    $observaciones = $_POST['Observaciones'] ?? [];
    $id_usuario = $_SESSION['id_usuario'];

    // Validar datos
    $total = count($nombres);
    if ($total === 0) {
        throw new Exception("No se recibieron datos de asistencia");
    }
    
    if (count($apellidos) != $total || count($cod_rutas) != $total || 
        count($estados) != $total || count($fechas) != $total) {
        throw new Exception("Los datos recibidos son inconsistentes");
    }

    // Preparar consulta (ahora incluye nombre_ruta)
    $sql = "INSERT INTO asistencias (
        Nombre, Apellido, Cod_Ruta, Nombre_Ruta, Estado, Fecha, 
        Hora_Inicio, Hora_Final, Observaciones, id_Usuario
    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    $stmt = $mysqli->prepare($sql);
    if (!$stmt) {
        throw new Exception("Error al preparar consulta: " . $mysqli->error);
    }

    // Transacción para integridad
    $mysqli->begin_transaction();

    for ($i = 0; $i < $total; $i++) {
        $stmt->bind_param("sssssssssi",
            $nombres[$i],
            $apellidos[$i],
            $cod_rutas[$i],
            $nombre_ruta, // Nombre de ruta (el mismo para todos)
            $estados[$i],
            $fechas[$i],
            $horas_inicio[$i],
            $horas_final[$i],
            $observaciones[$i],
            $id_usuario
        );

        if (!$stmt->execute()) {
            throw new Exception("Error al guardar registro $i: " . $stmt->error);
        }
    }

    $mysqli->commit();
    echo json_encode([
        'success' => true,
        'message' => "✅ $total asistencias guardadas",
        'data' => [
            'ruta' => $nombre_ruta,
            'fecha' => $fechas[0] ?? date('Y-m-d')
        ]
    ]);

} catch (Exception $e) {
    $mysqli->rollback();
    echo json_encode([
        'success' => false,
        'message' => '❌ Error: ' . $e->getMessage()
    ]);
} finally {
    if (isset($stmt)) $stmt->close();
    $mysqli->close();
}
?>