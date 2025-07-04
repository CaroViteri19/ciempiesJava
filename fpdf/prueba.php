<?php
require('fpdf.php');

class PDF_MC_Table extends FPDF
{
    protected $widths;
    protected $aligns;

        // PERSONALIZAR ENCABEZADO
    function Header()
    {
        // Logo (ajusta el path y tamaño)
        $this->Image('logo.jpeg', 10, 6, 30); // x, y, width

        // Nombre de la marca
        $this->SetFont('Arial', 'B', 14);
        $this->Cell(0, 10, 'SGI - INFORME DE ASISTENCIAS', 0, 1, 'C');

        // Información adicional
        $this->SetFont('Arial', '', 10);
        $this->Cell(0, 8, 'Direccion | Telefono | www.mimarca.com', 0, 1, 'C');
        
        // Línea separadora
        $this->Ln(5);
        $this->Line(10, $this->GetY(), 287, $this->GetY()); // Línea horizontal en A4 horizontal
        $this->Ln(5);
    }


    function SetWidths($w)
    {
        $this->widths = $w;
    }

    function SetAligns($a)
    {
        $this->aligns = $a;
    }

    function Row($data)
    {
        $nb = 0;
        for($i=0;$i<count($data);$i++)
            $nb = max($nb,$this->NbLines($this->widths[$i],$data[$i]));
        $h = 5 * $nb;
        $this->CheckPageBreak($h);
        for($i=0;$i<count($data);$i++)
        {
            $w = $this->widths[$i];
            $a = isset($this->aligns[$i]) ? $this->aligns[$i] : 'L';
            $x = $this->GetX();
            $y = $this->GetY();
            $this->Rect($x,$y,$w,$h);
            $this->MultiCell($w,5,$data[$i],0,$a);
            $this->SetXY($x+$w,$y);
        }
        $this->Ln($h);
    }

    function CheckPageBreak($h)
    {
        if($this->GetY()+$h>$this->PageBreakTrigger)
            $this->AddPage($this->CurOrientation);
    }

    function NbLines($w, $txt)
    {
        if(!isset($this->CurrentFont))
            $this->Error('No font has been set');
        $cw = $this->CurrentFont['cw'];
        if($w==0)
            $w = $this->w - $this->rMargin - $this->x;
        $wmax = ($w - 2*$this->cMargin) * 1000 / $this->FontSize;
        $s = str_replace("\r",'',(string)$txt);
        $nb = strlen($s);
        if($nb > 0 && $s[$nb - 1] == "\n")
            $nb--;
        $sep = -1;
        $i = 0;
        $j = 0;
        $l = 0;
        $nl = 1;
        while($i < $nb)
        {
            $c = $s[$i];
            if($c == "\n")
            {
                $i++;
                $sep = -1;
                $j = $i;
                $l = 0;
                $nl++;
                continue;
            }
            if($c == ' ')
                $sep = $i;
            $l += $cw[$c] ?? 0;
            if($l > $wmax)
            {
                if($sep == -1)
                {
                    if($i == $j)
                        $i++;
                }
                else
                    $i = $sep + 1;
                $sep = -1;
                $j = $i;
                $l = 0;
                $nl++;
            }
            else
                $i++;
        }
        return $nl;
    }
}

// Conexión a la base de datos
$conexion = new mysqli('localhost', 'root', '', 'ciempies');
if ($conexion->connect_error) {
    die('Error de conexión: ' . $conexion->connect_error);
}

// Consulta
<<<<<<< HEAD
$consulta = "SELECT Id_asistencia, Nombre_Estudiante, Id_Usuario, Cod_Ruta, Fecha, Hora_Inicio, Hora_Final, Estado 
             FROM asistencias 
             ORDER BY Fecha DESC, Hora_Inicio DESC";
$resultado = $conexion->query($consulta);



=======
$consulta = "SELECT Id_asistencia, Id_Estudiante, Id_Usuario, Id_Ruta, Fecha, Hora_Inicio, Hora_Final, Estado FROM asistencias";
$resultado = $conexion->query($consulta);

>>>>>>> 09644789d01d1ffc18b69311aed34a25bda3c3ea
// Crear PDF
$pdf = new PDF_MC_Table();
$pdf->AddPage('L', 'A4');
$pdf->SetFont('Arial', '', 12);
<<<<<<< HEAD
$pdf->SetWidths([30, 40, 35, 25, 35, 40, 40, 30]);

// Encabezado
$pdf->Row(['Id_asistencia', 'Nombre_Estudiante', 'Id_Usuario', 'Cod_Ruta', 'Fecha', 'Hora_Inicio', 'Hora_Final', 'Estado']);
=======
$pdf->SetWidths([35, 35, 35, 25, 35, 40, 40, 30]);

// Encabezado
$pdf->Row(['Id_asistencia', 'Id_Estudiante', 'Id_Usuario', 'Id_Ruta', 'Fecha', 'Hora_Inicio', 'Hora_Final', 'Estado']);
>>>>>>> 09644789d01d1ffc18b69311aed34a25bda3c3ea

// Contenido
while ($fila = $resultado->fetch_assoc()) {
    $pdf->Row([
        $fila['Id_asistencia'],
<<<<<<< HEAD
        $fila['Nombre_Estudiante'],
        $fila['Id_Usuario'],
        $fila['Cod_Ruta'],
=======
        $fila['Id_Estudiante'],
        $fila['Id_Usuario'],
        $fila['Id_Ruta'],
>>>>>>> 09644789d01d1ffc18b69311aed34a25bda3c3ea
        $fila['Fecha'],
        $fila['Hora_Inicio'],
        $fila['Hora_Final'],
        $fila['Estado']
    ]);
}

// Salida
$pdf->Output();
?>





