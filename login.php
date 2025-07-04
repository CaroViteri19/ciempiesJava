<?php
session_start();
include 'conexion.php';


// Obtener datos del formulario
$correo = $_POST['correo'] ?? '';
$contrasena = $_POST['contraseña'] ?? '';


// Validar campos vacíos
if (empty($correo) || empty($contrasena)) {
    echo "<script>alert('⚠️ Por favor completa todos los campos'); window.history.back();</script>";
    exit();
}


try {
    // Modificar la consulta para incluir el rol
    $sql = "SELECT id_usuario, email, rol FROM usuario WHERE email = ? AND contraseña = ?";
    $stmt = $mysqli->prepare($sql);


    if (!$stmt) {
        throw new Exception('Error en la preparación de la consulta SQL.');
    }


    $stmt->bind_param("ss", $correo, $contrasena);
    $stmt->execute();
    $result = $stmt->get_result();


    // Si hay coincidencia
    if ($result->num_rows === 1) {
        $usuario = $result->fetch_assoc();


        // Guardar datos en sesión
        $_SESSION['id_usuario'] = $usuario['id_usuario'];
        $_SESSION['email'] = $usuario['email'];
        $_SESSION['rol'] = $usuario['rol']; 


        echo "<script>
                alert('✅ Bienvenido al sistema');
                window.location.href = 'modulos.php';
              </script>";
        exit();


    } else {
        echo "<script>
                alert('❌ Datos incorrectos o no encontrados');
                window.history.back();
              </script>";
        exit();
    }


} catch (Exception $e) {
    echo "<script>
            alert('🚫 Error del sistema: " . $e->getMessage() . "');
            window.history.back();
          </script>";
    exit();
} finally {
    if (isset($stmt)) {
        $stmt->close();
    }
    $mysqli->close();
}
