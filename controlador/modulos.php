<?php
session_start();  // Muy importante que esté al principio

// Verifica que las variables de sesión estén definidas
$usuario = $_SESSION['email'] ?? 'Invitado';
$rol = $_SESSION['rol'] ?? 'Sin rol';

?>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Módulos CIEMPIÉS</title>
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
  <style>
    body::before {
      content: "";
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-size: cover;
      background-position: center;
      z-index: -2;
      filter: blur(8px);
    }
  </style>
</head>
<body class="min-h-screen bg-gradient-to-b from-pink-200 via-purple-200 to-blue-200">
  
<!-- Mensaje de bienvenida -->
<div class="text-right text-sm text-gray-700 font-semibold mb-4 pr-4 pt-2">
  <p>Usuario: <?php echo htmlspecialchars($usuario); ?></p>
  <p>Rol: <?php echo htmlspecialchars($rol); ?></p>
</div>

<!-- Encabezado -->
<div class="max-w-7xl mx-auto mt-6 px-4 mb-10">
  <div class="flex flex-col sm:flex-row items-center space-y-4 sm:space-y-0 sm:space-x-6">
    <img src="../assets/logo.jpeg" alt="Logo SGI" class="w-32 h-32 object-contain rounded-full shadow-xl">
    <a href="../views/index.html" class="flex items-center space-x-2 bg-cyan-300 text-white font-semibold px-4 py-2 rounded-full shadow hover:bg-cyan-400 transition transform hover:scale-105">
      <img src="../assets/pag_inicio.png" alt="." class="h-5">
      <span>Página Inicio</span>
    </a>
  </div>
</div>

<!-- Tarjetas -->
<div class="max-w-6xl mx-auto grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-10">

  <!-- Tarjeta 1: Inscripción -->
  <a href="../views/form_ins.php" class="block rounded-xl overflow-hidden shadow-lg hover:shadow-2xl transform hover:-translate-y-2 transition-all duration-300 bg-white/20 backdrop-blur-md border border-white/30">
    <img src="../assets/inscripcion.jpeg" alt="Inscripción" class="w-full h-40 object-cover">
    <div class="p-4">
      <h3 class="text-lg font-semibold mb-2">Inscripción de estudiantes</h3>
      <p class="text-sm mb-4">Registra a los estudiantes en el sistema educativo de movilidad segura.</p>
      <div class="flex items-center mt-4">
        <img class="w-8 h-8 rounded-full" src="../assets/logo_usuario.jpeg" alt="Autor">
        <div class="ml-2 text-sm">
          <p>Administrador, Apoyo territorial</p>
          <p class="text-xs opacity-80">Abr 9</p>
        </div>
      </div>
    </div>
  </a>

  <!-- Tarjeta 2: Información Estudiante -->
  <a href="../views/form_estud.php" class="block rounded-xl overflow-hidden shadow-lg hover:shadow-2xl transform hover:-translate-y-2 transition-all duration-300 bg-white/20 backdrop-blur-md border border-white/30">
    <img src="../assets/estudiante.jpeg" alt="Estudiante" class="w-full h-40 object-cover">
    <div class="p-4">
      <h3 class="text-lg font-semibold mb-2">Información del estudiante</h3>
      <p class="text-sm mb-4">Consulta o edita datos del estudiante dentro del sistema CIEMPIÉS.</p>
      <div class="flex items-center mt-4">
        <img class="w-8 h-8 rounded-full" src="../assets/logo_usuario.jpeg" alt="Autor">
        <div class="ml-2 text-sm">
          <p>Administrador, Apoyo territorial</p>
          <p class="text-xs opacity-80">Abr 9</p>
        </div>
      </div>
    </div>
  </a>

  <!-- Tarjeta 3: Asistencia -->
  
  <a href="../views/asistencia.php" class="block rounded-xl overflow-hidden shadow-lg hover:shadow-2xl transform hover:-translate-y-2 transition-all duration-300 bg-white/20 backdrop-blur-md border border-white/30">
    <img src="../assets/asistencia.jpeg" alt="Asistencia" class="w-full h-40 object-cover">
    <div class="p-4">
      <h3 class="text-lg font-semibold mb-2">Registro de asistencia</h3>
      <p class="text-sm mb-4">Controla y revisa la asistencia diaria de los estudiantes.</p>
      <div class="flex items-center mt-4">
        <img class="w-8 h-8 rounded-full" src="../assets/logo_usuario.jpeg" alt="Autor">
        <div class="ml-2 text-sm">
          <p>Monitor</p>
          <p class="text-xs opacity-80">Abr 9</p>
        </div>
      </div>
    </div>
  </a>
  

</div>
</body>
</html>
