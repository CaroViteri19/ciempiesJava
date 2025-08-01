<?php
// Iniciar sesión para mantener el estado del usuario
session_start();

// Verificar si se envió el formulario
if (!empty($_POST["inicio"])) {
    // Validar campos vacíos (sintaxis corregida)
    if (empty($_POST["usuario"]) || empty($_POST["contraseña"])) {
        echo "CAMPOS VACIOS";
    } else {
        // Obtener y sanitizar datos del formulario
        $usuario = trim($_POST["usuario"]);
        $contraseña = $_POST["contraseña"];
        
        
        // Consulta preparada para seguridad (corrección de inyección SQL)
        $sql = $conexion->prepare("SELECT * FROM usuario WHERE email = ? AND contraseña = ? LIMIT 1");
        $sql->bind_param("ss", $usuario, $contraseña);
        $sql->execute();
        $resultado = $sql->get_result();
        
        // Verificar si se encontró el usuario 
        if ($datos = $resultado->fetch_object()) {
            // Guardar datos de usuario en sesión
            $_SESSION['usuario'] = $datos->usuario;
            $_SESSION['id_usuario'] = $datos->id; 

            // Redireccionar 
            header("Location: modulos.html");
            exit();
        } else {
            echo "ACCESO DENEGADO";
        }
        
        // Cerrar statement
        $sql->close();
    }
}

// Cerrar conexión 
$conexion->close();
?>