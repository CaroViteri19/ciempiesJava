<?php
session_start();

$rol = $_SESSION['rol'] ?? '';
$roles_autorizados = ['administrador', 'apoyo territorial'];

// Si no tiene permiso
if (!in_array($rol, $roles_autorizados)) {
    echo "<script>
            alert('⛔ Acceso denegado para: $rol');
            window.location.href = 'modulos.php';
          </script>";
    exit();
}

// Si tiene permiso
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
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Registro de Estudiantes</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gradient-to-b from-pink-100 to-blue-100 min-h-screen p-4">
  
 <!-- Logo y botón juntos -->
<div class="fixed top-4 left-4 z-50 flex items-center space-x-4">
  <!-- Logo -->
  <img src="../assets/logo.jpeg" alt="Logo" class="h-20 w-20 rounded-full object-cover shadow-md">

  <!-- Botón de regreso -->
  <a href="../controlador/modulos.php" class="flex items-center space-x-2 bg-pink-100 text-white font-semibold px-4 py-2 rounded-full shadow hover:bg-cyan-600 transition transform hover:scale-105">
    <img src="../assets/click.png" alt="Inicio" class="h-5">
    <span>volver</span>
  </a>
</div>


  <div class="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow-lg">
    <h2 class="text-2xl font-bold text-purple-700 text-center">Formulario de Registro de Estudiantes</h2>

    <!-- ✅ Mensaje de registro -->
    <div id="mensaje" class="text-green-600 font-semibold text-center mt-4"></div>

    <!-- ✅ FORMULARIO -->
    <form id="formEstudiante" class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
      <input type="text" name="nombre" placeholder="Nombre" required class="p-2 border rounded" />
      <input type="text" name="apellido" placeholder="Apellido" required class="p-2 border rounded" />
      <input type="text" name="documento" placeholder="Documento" required class="p-2 border rounded" />
      <!-- Sexo -->
      <select name="sexo" required class="p-2 border rounded">
        <option value="">Sexo</option>
        <option value="Femenino">Femenino</option>
        <option value="Masculino">Masculino</option>
        <option value="Otro">Otro</option>
      </select>
      <input type="text" name="eps" placeholder="EPS" required class="p-2 border rounded" />
      <input type="text" name="direccion" placeholder="Dirección" required class="p-2 border rounded" />
      <input type="number" name="edad" placeholder="Edad" required class="p-2 border rounded" />
      <input type="text" name="discapacidad" placeholder="Discapacidad" class="p-2 border rounded" />
      <input type="text" name="etnia" placeholder="Etnia" class="p-2 border rounded" />
      <input type="text" name="curso" placeholder="Curso" required class="p-2 border rounded" />
      <input type="tel" name="telefono" placeholder="Teléfono" required class="p-2 border rounded" />
      <!-- Código de ruta -->
        <select id="Cod_Ruta" name="Cod_Ruta" required class="p-2 border rounded">
          <option value="" >Codigo de la Ruta</option>
          <option value="101">RT001</option>
          <option value="102">RT002</option>
          <option value="103">RT003</option>
          <option value="104">RT004</option>
          <option value="105">RT005</option>
          <option value="106">RT006</option>
          <option value="107">RT007</option>
          <option value="108">RT008</option>
          <option value="109">RT009</option>
        </select>
     <!-- Nombre de ruta -->
        <select id="nombreRuta" name="Nombre_ruta" required class="p-2 border rounded">
          <option value="">Nombre Ruta</option>
          <option value="Ruta Norte">Ruta Norte</option>
          <option value="Ruta Sur">Ruta Sur</option>
          <option value="Ruta Centro">Ruta Este</option>
          <option value="Ruta Norte">Ruta Oeste</option>
          <option value="Ruta Sur">Ruta Centro</option>
          <option value="Ruta Centro">Ruta Nororiente</option>
          <option value="Ruta Norte">Ruta Noroccidente</option>
          <option value="Ruta Sur">Ruta Suroriente</option>
          <option value="Ruta Centro">Ruta Suroccidente</option>
        </select>
    <!-- Estado -->
 <select name="Estado" required class="p-2 border rounded">
        <option value="">Estado</option>
        <option value="Pendiente">Pendiente</option>
      </select>
      

      <div class="md:col-span-2 text-center mt-4">
        <button type="submit" class="bg-purple-600 text-white font-bold px-6 py-3 rounded-full shadow-lg hover:shadow-xl hover:bg-purple-700 transition duration-300 transform hover:-translate-y-1">
          Registrar
        </button>
      </div>
    </form>

    <!-- ✅ Tabla de estudiantes -->
    <h3 class="text-xl font-semibold mt-8 mb-4 text-gray-800">Listado de Estudiantes Registrados</h3>
    <div class="overflow-x-auto">
      <table class="min-w-full bg-white border border-gray-200 rounded">
        <thead class="bg-purple-600 text-white">
          <tr>
            <th class="px-2 py-1 border">Nombre</th>
            <th class="px-2 py-1 border">Apellido</th>
            <th class="px-2 py-1 border">Documento</th>
            <th class="px-2 py-1 border">Sexo</th>
            <th class="px-2 py-1 border">EPS</th>
            <th class="px-2 py-1 border">Dirección</th>
            <th class="px-2 py-1 border">Edad</th>
            <th class="px-2 py-1 border">Discapacidad</th>
            <th class="px-2 py-1 border">Etnia</th>
            <th class="px-2 py-1 border">Curso</th>
            <th class="px-2 py-1 border">Teléfono</th>
          </tr>
        </thead>
        <tbody id="tablaEstudiantes"></tbody>
      </table>
    </div>
  </div>

  <!-- ✅ SCRIPT PARA ENVIAR Y ACTUALIZAR TABLA -->
  <script>
    document.getElementById("formEstudiante").addEventListener("submit", function(e) {
      e.preventDefault();

      const form = e.target;
      const formData = new FormData(form);
      const mensaje = document.getElementById("mensaje");

      fetch("registro.php", {
        method: "POST",
        body: formData
      })
      .then(res => res.text())
      .then(data => {
        mensaje.textContent = "✅ Registrado exitosamente";
        form.reset();
        cargarEstudiantes(); // Actualiza la tabla
      })
      .catch(() => {
        mensaje.textContent = "❌ Error al registrar";
      });
    });

    function cargarEstudiantes() {
      fetch("listar_estud.php")
        .then(res => res.text())
        .then(data => {
          document.getElementById("tablaEstudiantes").innerHTML = data;
        });
    }

    window.addEventListener("DOMContentLoaded", cargarEstudiantes);
  </script>
</body>
</html>
