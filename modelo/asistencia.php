
<?php
session_start();
include 'conexion.php'; 

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
  <meta charset="UTF-8">
  <title>Tomar Asistencia</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
</head>
<body class="bg-gradient-to-br from-pink-200 to-blue-100 min-h-screen p-6">

<!-- Logo y Botón Volver -->
  <div class="flex items-center space-x-4 mb-6">
    <!-- Logo (ajusta el src a tu logo real) -->
    <img src="logo.jpeg" alt="Logo SGI" class="w-16 h-16 rounded-full shadow-md" />

    <!-- Botón volver -->
    <a href="modulos.php" class="flex items-center space-x-2 bg-pink-100 px-4 py-2 rounded-full shadow hover:bg-pink-200 transition">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-black" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7m-9 2v6m0 0h4m-4 0a2 2 0 01-2-2v-4m6 0v6a2 2 0 002 2h4a2 2 0 002-2v-6m-6 0V5a2 2 0 012-2h2a2 2 0 012 2v6" />
      </svg>
      <span class="text-black font-semibold">volver</span>
    </a>
  </div>


  <div class="max-w-5xl mx-auto bg-white p-4 rounded shadow">
    <h2 class="text-2xl font-bold mb-4 text-center">Registro de Asistencia</h2>

    <!-- Filtros -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
      <div>
        <label class="block text-sm font-medium text-gray-700">Ruta</label>
        <select id="filtroRuta" class="border rounded w-full p-2">
          <option value="" >Codigo de la Ruta</option>
          
        </select>
      </div>
     <div>
  <label class="block text-sm font-medium text-gray-700">Nombre Ruta</label>
  <select name="nombre_ruta" class="border rounded w-full p-2">
    <option value="Ruta Norte">Ruta Norte</option>
    <option value="Ruta Sur">Ruta Sur</option>
    <option value="Ruta Este">Ruta Este</option>
    <option value="Ruta Oeste">Ruta Oeste</option>
    <option value="Ruta Centro">Ruta Centro</option>
    <option value="Ruta Nororiente">Ruta Nororiente</option>
    <option value="Ruta Noroccidente">Ruta Noroccidente</option>
    <option value="Ruta Suroriente">Ruta Suroriente</option>
    <option value="Ruta Suroccidente">Ruta Suroccidente</option>
  </select>
</div>
      <div>
        <label class="block text-sm font-medium text-gray-700">Fecha</label>
        <input type="date" id="filtroFecha" class="border rounded w-full p-2" value="<?php echo date('Y-m-d'); ?>">
      </div>
    </div>

    <form id="formAsistencia">

    

      <!-- Tabla de asistencia -->
<div class="col-span-2 overflow-x-auto">
  <table class="min-w-full bg-white border" id="tablaPDF">
    <thead>
      <tr class="bg-purple-200 text-gray-700">
        <th class="px-2 py-1 border">Nombre</th>
        <th class="px-2 py-1 border">Apellido</th>
        <th class="px-2 py-1 border">Ruta</th>
        <th class="px-2 py-1 border">Curso</th>
        <th class="px-2 py-1 border">Estado</th>
        <th class="px-2 py-1 border">Observación</th>
          <th class="px-2 py-1 border">Hora Inicio</th>
    <th class="px-2 py-1 border">Hora Final</th>
      </tr>
    </thead>
    <tbody id="tablaAsistencia">
      <!-- Las filas se insertarán dinámicamente -->
    </tbody>
  </table>
  <div class="mt-6 flex justify-end gap-4">
  <!-- Botón Guardar (verde) -->
  <button type="submit" class="bg-green-500 text-white px-6 py-2 rounded-full shadow-md hover:bg-green-600 transition">
    Guardar
  </button>

  <!-- Botón Cancelar (rojo) -->
  <button type="button" onclick="cancelarFormulario()" class="bg-red-500 text-white px-6 py-2 rounded-full shadow-md hover:bg-red-600 transition">
    Cancelar
  </button>

  <!-- Botón PDF (azul) -->
  <button type="button" onclick="generarPDF()" class="bg-blue-500 text-white px-6 py-2 rounded-full shadow-md hover:bg-blue-600 transition">
    Reporte PDF
  </button>
</div>
</div>

  <script>
    let estudiantes = [];


    function cargarEstudiantes() {
      fetch('listar_estud_ruta.php')
        .then(res => res.json())
        .then(data => {
          estudiantes = data;
          cargarFiltros(data);
          renderizarTabla();
        });
    }

    function cargarFiltros(data) {
  const rutasUnicas = [...new Set(data.map(e => e.Id_Ruta))];
  const cursosUnicos = [...new Set(data.map(e => e.Curso))];

  const rutaSelect = document.getElementById('filtroRuta');
  rutaSelect.innerHTML = '<option value="">Código de la Ruta</option>';

  rutasUnicas.forEach(idRuta => {
    if (rutaMap[idRuta]) {
      const option = document.createElement('option');
      option.value = idRuta; // <- valor numérico
      option.textContent = rutaMap[idRuta]; // <- texto RT00X
      rutaSelect.appendChild(option);
    }
  });

  const cursoSelect = document.getElementById('filtroCurso');
  cursoSelect.innerHTML = '<option value="">Todos</option>';
  cursosUnicos.forEach(curso => {
    const option = document.createElement('option');
    option.value = curso;
    option.textContent = curso;
    cursoSelect.appendChild(option);
  });
}

// Mapeo de Id_Ruta a nombre legible
const rutaMap = {
  1: "RT101",
  2: "RT102",
  3: "RT103",
  4: "RT104",
  5: "RT105",
  6: "RT106",
  7: "RT107",
  8: "RT108",
  9: "RT109"
};

// Mapeo inverso: RT001 → 101
const rutaMapInverso = Object.fromEntries(
  Object.entries(rutaMap).map(([id, nombre]) => [nombre, Number(id)])
);


    function renderizarTabla() {
  const rutaTexto = document.getElementById('filtroRuta').value;
 
  const fecha = document.getElementById('filtroFecha').value;

  


  const tbody = document.getElementById('tablaAsistencia');
  tbody.innerHTML = '';

  estudiantes
   .filter(est =>
  (!rutaTexto || est.Id_Ruta == parseInt(rutaTexto))
)

    .forEach(est => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td class="border px-2 py-1">${est.nombre}</td>
        <td class="border px-2 py-1">${est.apellido}</td>
        <td class="border px-2 py-1">${rutaMap[est.Id_Ruta]}</td>
        <td class="border px-2 py-1">${est.curso}</td>
        
        
        <input type="hidden" name="Nombre[]" value="${est.nombre}">
  <input type="hidden" name="Apellido[]" value="${est.apellido}">
  <input type="hidden" name="Cod_Ruta[]" value="${100 + parseInt(est.Id_Ruta)}">
  <input type="hidden" name="Fecha[]" value="${fecha}">
  
  
  <!-- Datos del formulario -->
  <td>
    <select name="Estado[]" class="...">
      <option value="Presente">Presente</option>
      <option value="Ausente">Ausente</option>
    </select>
  </td>
  <td>
    <input type="text" name="Observaciones[]" class="...">
  </td>
  <td>
    <input type="time" name="Hora_Inicio[]" class="...">
  </td>
  <td>
    <input type="time" name="Hora_Final[]" class="...">
  </td>
</tr>
`;
      tbody.appendChild(tr);
    });
}
    document.getElementById('filtroRuta').addEventListener('change', renderizarTabla);
    
    document.getElementById('filtroFecha').addEventListener('change', renderizarTabla);

    document.getElementById('formAsistencia').addEventListener('submit', function(e) {
      e.preventDefault();
      const form = e.target;

       const nombreRutaSelect = document.querySelector('select[name="nombre_ruta"]');
  const inputRutaOculta = document.createElement('input');
  inputRutaOculta.type = 'hidden';
  inputRutaOculta.name = 'nombre_ruta';
  inputRutaOculta.value = nombreRutaSelect.value;
  form.appendChild(inputRutaOculta);
      const formData = new FormData(this);
      fetch('guardasisten.php', {
    method: 'POST',
    body: formData
  })
  .then(res => res.json())
.then(data => {
  console.log(data); // Ver qué viene desde PHP
  if (data.success) {
    alert(data.message);
  } else {
    alert(data.message); // Ver mensaje detallado de error del PHP
  }
})
  .catch(err => {
   alert('Error al guardar la asistencia');
    console.error(err);
  });
});

    function generarPDF() {
      const { jsPDF } = window.jspdf;
      const doc = new jsPDF();
      doc.text("Reporte de Asistencia", 10, 10);

      const table = document.getElementById("tablaPDF");
      const rows = Array.from(table.querySelectorAll("tbody tr")).map(row =>
        Array.from(row.querySelectorAll("td")).map(cell => cell.innerText)
      );

      let y = 20;
      rows.forEach((row, index) => {
        doc.text(row.join("  |  "), 10, y);
        y += 10;
      });

      doc.save("reporte_asistencia.pdf");
    }

    cargarEstudiantes();
  </script>
</body>
</html>