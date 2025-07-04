
  <?php
session_start();
include 'conexion.php'; 

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $Cod_Ruta = $_POST['Cod_Ruta'] ?? '';
    $Nombre_ruta = $_POST['Nombre_ruta'] ?? '';
    $Nombre_Estudiante = $_POST['Nombre_Estudiante'] ?? '';
    $Estado = $_POST['Estado'] ?? '';
    $Fecha = $_POST['Fecha'] ?? '';
    $Hora_Inicio = $_POST['Hora_Inicio'] ?? '';
    $Hora_Final = $_POST['Hora_Final'] ?? '';
    $Observaciones = $_POST['Observaciones'] ?? '';
    $Id_Usuario = $_POST['Id_Usuario'] ?? '';

    $sql = "INSERT INTO asistencias (
                  Cod_Ruta, Nombre_ruta, Nombre_Estudiante,
                Estado, Fecha, Hora_Inicio, Hora_Final,
                Observaciones, Id_Usuario
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


    $stmt = $mysqli->prepare($sql);
    $resultado = $stmt->execute([
        $Cod_Ruta, $Nombre_ruta, $Nombre_Estudiante,
        $Estado, $Fecha, $Hora_Inicio, $Hora_Final,
        $Observaciones, $Id_Usuario
    ]);
  }


$rol = $_SESSION['rol'] ?? '';
$roles_autorizados = ['monitor', 'administrador'];

// Si el usuario no tiene permiso
if (!in_array($rol, $roles_autorizados)) {
    echo "<script>
            alert('⛔ Acceso denegado para: $rol');
            window.location.href = 'modulos.php';
          </script>";
    exit();
}

//  alerta de bienvenida
echo "<script>
        alert('✅ Bienvenido: $rol');
      </script>";
?>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Registro de Asistencia</title>
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
  <a href="modulos.php" class="flex items-center space-x-2 bg-cyan-500 text-white font-semibold px-4 py-2 rounded-full shadow hover:bg-cyan-600 transition transform hover:scale-105">
    <img src="pag_inicio.png" alt="Inicio" class="h-5">
    <span>volver</span>
  </a>
</div>


  <!-- Contenedor del formulario -->
  <div class="max-w-2xl mx-auto mt-20 bg-white shadow-lg rounded-xl p-8 space-y-6 transition-all duration-300 hover:shadow-2xl relative">
    <h2 class="text-2xl font-bold text-purple-700 text-center">Registro de Asistencia</h2>

    <form id="asistencia-form" class="grid grid-cols-1 md:grid-cols-2 gap-6" method="POST">

      <!-- Código de ruta -->
      <div>
        <label class="block text-sm font-medium text-gray-700">Código de Ruta</label>
        <select id="codigoRuta" name="Cod_Ruta" class="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400 transition">
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
      </div>

      <!-- Nombre de ruta -->
      <div>
        <label class="block text-sm font-medium text-gray-700">Nombre de Ruta</label>
        <select id="nombreRuta" name="Nombre_ruta" class="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400 transition">
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
      </div>

      <!-- Nombre del estudiante -->
     <div>
  <label class="block text-sm font-medium text-gray-700">Nombre estudiante</label>
  <input
    type="text"
    id="nombreEstudiante"
    name="Nombre_Estudiante"
    placeholder="Escribe el nombre del estudiante"
    class="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400 transition"
  />
</div>


      <!-- Estado -->
      <div>
        <label class="block text-sm font-medium text-gray-700">Estado</label>
        <select id="estado" name="Estado" class="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400 transition">
          <option>Presente</option>
          <option>Ausente</option>
        </select>
      </div>

      <!-- Fecha automática -->
      <div>
        <label class="block text-sm font-medium text-gray-700">Fecha</label>
        <input type="text" id="fecha-auto" name="Fecha" class="mt-1 w-full px-4 py-2 border rounded-lg bg-gray-100 cursor-not-allowed" readonly>
      </div>



      <!-- Hora inicio -->
      <div>
  <label class="block text-sm font-medium text-gray-700">Hora_Inicio</label>
  <input
    type="text"
    id="Hora_inicio"
    name="Hora_Inicio"
    placeholder="********"
    class="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400 transition"
  />
</div>


      <!-- Hora final-->
   <div>
  <label class="block text-sm font-medium text-gray-700">Hora_Final</label>
  <input
    type="text"
    id="Hora_final"
    name="Hora_Final"
    placeholder="********"
    class="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400 transition"
  />
</div>


      
      <!-- Observacion -->
     <div>
  <label class="block text-sm font-medium text-gray-700">Observacion</label>
  <input
    type="text"
    id="Observacion"
    name="Observaciones"
    placeholder="Escriba su observacion"
    class="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400 transition"
  />
</div>
<!-- Monitor -->
<div>
  <label class="block text-sm font-medium text-gray-700">Codigo Monitor</label>
  <input
    type="text"
    id="Id_Usuario"
    name="Id_Usuario"
    placeholder="Escribe tu Codigo"
    class="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400 transition"
  />
</div>
    </form>

    <!-- Sección de resultados -->
    <div id="resultado" class="mt-6 p-4 bg-purple-100 rounded-lg hidden">
      <h3 class="text-lg font-semibold text-purple-800 mb-2">Datos Registrados:</h3>
      <ul class="list-disc list-inside text-purple-900" id="datosRegistrados"></ul>
    </div>
  </div>

  <!-- Botones flotantes -->
  <div class="fixed bottom-6 right-6 z-50 flex flex-col space-y-4 items-end">
    <!-- Botón Registrar -->
    <button
      type="submit"
      form="asistencia-form"
      title="Registrar asistencia del estudiante"
      class="bg-purple-600 text-white font-bold px-6 py-3 rounded-full shadow-lg hover:shadow-xl hover:bg-purple-700 transition duration-300 transform hover:-translate-y-1 flex items-center gap-2"
      onclick="vibrar()"
    >
      <i data-lucide="check-circle" class="w-5 h-5"></i>
      Registrar
    </button>

    <!-- Botón Cancelar -->
    <button
      type="button"
      title="Cancelar y reiniciar formulario"
      class="bg-red-500 text-white font-bold px-6 py-3 rounded-full shadow-lg hover:shadow-xl hover:bg-red-600 transition duration-300 transform hover:-translate-y-1 flex items-center gap-2"
      onclick="location.reload(); vibrar();"
    >
      <i data-lucide="x-circle" class="w-5 h-5"></i>
      Cancelar
    </button>

    <!-- Botón Reporte -->
  
  <a href="fpdf/prueba.php" target="_blank"
     class="bg-blue-600 text-white font-bold px-6 py-3 rounded-full shadow-lg hover:shadow-xl hover:bg-blue-700 transition duration-300 transform hover:-translate-y-1 flex items-center gap-2">
    <i data-lucide="file-text" class="w-5 h-5"></i>
    Generar Reporte PDF
  </a>
  </div>

  <!-- Toast -->
  <div id="toast" class="fixed bottom-24 right-6 bg-green-500 text-white font-semibold px-6 py-3 rounded-lg shadow-lg opacity-0 pointer-events-none transition-opacity duration-500 z-50">
    ✅ ¡Asistencia registrada correctamente!
  </div>

  <script>
    // Activar íconos
    lucide.createIcons();

    // Fecha automática
    const fechaInput = document.getElementById('fecha-auto');
    const hoy = new Date();
    const fechaFormateada = hoy.toISOString().split('T')[0];
    fechaInput.value = fechaFormateada;

    // Función de vibración
    function vibrar() {
      if ("vibrate" in navigator) {
        navigator.vibrate(50);
      }
    }

  
    

  </script>
</body>
</html>
