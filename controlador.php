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
        
        // CONEXIÓN A LA BASE DE DATOS (debe estar definida previamente)
        // $conexion = new mysqli("host", "usuario", "contraseña", "basedatos");
        
        // Consulta preparada para seguridad (corrección de inyección SQL)
        $sql = $conexion->prepare("SELECT * FROM usuario WHERE email = ? AND contraseña = ? LIMIT 1");
        $sql->bind_param("ss", $usuario, $contraseña);
        $sql->execute();
        $resultado = $sql->get_result();
        
        // Verificar si se encontró el usuario (sintaxis corregida)
        if ($datos = $resultado->fetch_object()) {
            // Guardar datos de usuario en sesión
            $_SESSION['usuario'] = $datos->usuario;
            $_SESSION['id_usuario'] = $datos->id; // Asumiendo que hay un campo 'id'
            
            // Redireccionar (sintaxis corregida)
            header("Location: modulos.html");
            exit(); // Importante después de header
        } else {
            echo "ACCESO DENEGADO";
        }
        
        // Cerrar statement
        $sql->close();
    }
}

// Cerrar conexión (asumiendo que $conexion está definida en otro archivo)
$conexion->close();
?>