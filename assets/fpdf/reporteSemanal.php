<?php
ob_start(); // Previene errores de salida antes del PDF

require('fpdf.php');

class PDF_MC_Table extends FPDF
{
    protected $widths;
    protected $aligns;

    function Header()
    {
        $this->Image('logo.jpeg', 10, 6, 30);
        $this->SetFont('Arial', 'B', 14);
        $this->Cell(0, 10, 'SGI - INFORME DE ASISTENCIAS', 0, 1, 'C');
        $this->SetFont('Arial', '', 10);
        $this->Cell(0, 8, 'Direccion | Telefono | www.mimarca.com', 0, 1, 'C');
        $this->Ln(5);
        $this->Line(10, $this->GetY(), 287, $this->GetY());
        $this->Ln(5);
    }

    function SetWidths($w) { $this->widths = $w; }
    function SetAligns($a) { $this->aligns = $a; }

    function Row($data)
    {
        $nb = 0;
        for ($i = 0; $i < count($data); $i++)
            $nb = max($nb, $this->NbLines($this->widths[$i], $data[$i]));
        $h = 5 * $nb;
        $this->CheckPageBreak($h);
        for ($i = 0; $i < count($data); $i++) {
            $w = $this->widths[$i];
            $a = isset($this->aligns[$i]) ? $this->aligns[$i] : 'L';
            $x = $this->GetX();
            $y = $this->GetY();
            $this->Rect($x, $y, $w, $h);
            $this->MultiCell($w, 5, utf8_decode($data[$i]), 0, $a);
            $this->SetXY($x + $w, $y);
        }
        $this->Ln($h);
    }

    function CheckPageBreak($h)
    {
        if ($this->GetY() + $h > $this->PageBreakTrigger)
            $this->AddPage($this->CurOrientation);
    }

    function NbLines($w, $txt)
    {
        if (!isset($this->CurrentFont))
            $this->Error('No font has been set');
        $cw = $this->CurrentFont['cw'];
        if ($w == 0)
            $w = $this->w - $this->rMargin - $this->x;
        $wmax = ($w - 2 * $this->cMargin) * 1000 / $this->FontSize;
        $s = str_replace("\r", '', (string)$txt);
        $nb = strlen($s);
        if ($nb > 0 && $s[$nb - 1] == "\n")
            $nb--;
        $sep = -1;
        $i = 0;
        $j = 0;
        $l = 0;
        $nl = 1;
        while ($i < $nb) {
            $c = $s[$i];
            if ($c == "\n") {
                $i++;
                $sep = -1;
                $j = $i;
                $l = 0;
                $nl++;
                continue;
            }
            if ($c == ' ')
                $sep = $i;
            $l += $cw[$c] ?? 0;
            if ($l > $wmax) {
                if ($sep == -1) {
                    if ($i == $j)
                        $i++;
                } else
                    $i = $sep + 1;
                $sep = -1;
                $j = $i;
                $l = 0;
                $nl++;
            } else
                $i++;
        }
        return $nl;
    }
}

// Conexión
$conexion = new mysqli('localhost', 'root', '', 'ciempies');
if ($conexion->connect_error) {
    die('Error de conexión: ' . $conexion->connect_error);
}

// Consulta
$consulta = "SELECT Id_asistencia, Nombre_Estudiante, Id_Usuario, Cod_Ruta, Fecha, Hora_Inicio, Hora_Final, Estado 
             FROM asistencias 
             ORDER BY Fecha DESC, Hora_Inicio DESC";
$resultado = $conexion->query($consulta);

// Crear PDF
$pdf = new PDF_MC_Table();
$pdf->AddPage('L', 'A4');
$pdf->SetFont('Arial', '', 10);

// Establecer anchos para 8 columnas
$pdf->SetWidths([30, 50, 25, 25, 35, 25, 25, 25]);

// Encabezado
$pdf->Row([
    'Id_asistencia', 
    'Nombre_Estudiante', 
    'Id_Usuario', 
    'Cod_Ruta', 
    'Fecha', 
    'Hora_Inicio', 
    'Hora_Final', 
    'Estado'
]);

// Contenido
while ($fila = $resultado->fetch_assoc()) {
    $pdf->Row([
        $fila['Id_asistencia'],
        $fila['Nombre_Estudiante'],
        $fila['Id_Usuario'],
        $fila['Cod_Ruta'],
        $fila['Fecha'],
        $fila['Hora_Inicio'],
        $fila['Hora_Final'],
        $fila['Estado']
    ]);
}

ob_end_clean(); // Limpia el búfer para evitar errores
$pdf->Output();
?>
