package com.ciempies.sgi.controller;

import com.ciempies.sgi.entity.Asistencia;
import com.ciempies.sgi.entity.Estudiante;
import com.ciempies.sgi.entity.Inscripcion;
import com.ciempies.sgi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private RutaRepository rutaRepository;
    
    @Autowired
    private AsistenciaRepository asistenciaRepository;
    
    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    @Autowired
    private ZonaRepository zonaRepository;
    
    @GetMapping("/estadisticas-generales")
    public ResponseEntity<Map<String, Object>> getEstadisticasGenerales() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        // Estadísticas de usuarios
        long totalUsuarios = usuarioRepository.count();
        
        // Estadísticas de estudiantes
        long totalEstudiantes = estudianteRepository.count();
        long estudiantesFemeninos = estudianteRepository.countBySexo(Estudiante.Sexo.femenino);
        long estudiantesMasculinos = estudianteRepository.countBySexo(Estudiante.Sexo.masculino);
        
        // Estadísticas de rutas
        long totalRutas = rutaRepository.count();
        
        // Estadísticas de asistencias (hoy)
        LocalDate hoy = LocalDate.now();
        long asistenciasHoy = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.presente, hoy) +
                             asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.ausente, hoy);
        long presentesHoy = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.presente, hoy);
        long ausentesHoy = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.ausente, hoy);
        
        // Estadísticas de inscripciones
        long totalInscripciones = inscripcionRepository.count();
        long inscripcionesActivas = inscripcionRepository.countByEstado(Inscripcion.Estado.activo);
        long inscripcionesPendientes = inscripcionRepository.countByEstado(Inscripcion.Estado.pendiente);
        long inscripcionesRechazadas = inscripcionRepository.countByEstado(Inscripcion.Estado.rechazado);
        
        // Estadísticas de zonas
        long totalZonas = zonaRepository.count();
        
        // Agregar todas las estadísticas al mapa
        estadisticas.put("usuarios", Map.of(
            "total", totalUsuarios
        ));
        
        estadisticas.put("estudiantes", Map.of(
            "total", totalEstudiantes,
            "femeninos", estudiantesFemeninos,
            "masculinos", estudiantesMasculinos
        ));
        
        estadisticas.put("rutas", Map.of(
            "total", totalRutas
        ));
        
        estadisticas.put("asistencias", Map.of(
            "hoy", Map.of(
                "total", asistenciasHoy,
                "presentes", presentesHoy,
                "ausentes", ausentesHoy,
                "porcentajeAsistencia", asistenciasHoy > 0 ? (double) presentesHoy / asistenciasHoy * 100 : 0
            )
        ));
        
        estadisticas.put("inscripciones", Map.of(
            "total", totalInscripciones,
            "activas", inscripcionesActivas,
            "pendientes", inscripcionesPendientes,
            "rechazadas", inscripcionesRechazadas,
            "porcentajeAprobacion", totalInscripciones > 0 ? (double) inscripcionesActivas / totalInscripciones * 100 : 0
        ));
        
        estadisticas.put("zonas", Map.of(
            "total", totalZonas
        ));
        
        return ResponseEntity.ok(estadisticas);
    }
    
    @GetMapping("/estadisticas-fecha/{fecha}")
    public ResponseEntity<Map<String, Object>> getEstadisticasByFecha(@PathVariable String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        Map<String, Object> estadisticas = new HashMap<>();
        
        long asistenciasTotal = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.presente, localDate) +
                               asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.ausente, localDate);
        long presentes = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.presente, localDate);
        long ausentes = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.ausente, localDate);
        
        estadisticas.put("fecha", fecha);
        estadisticas.put("totalAsistencias", asistenciasTotal);
        estadisticas.put("presentes", presentes);
        estadisticas.put("ausentes", ausentes);
        estadisticas.put("porcentajeAsistencia", asistenciasTotal > 0 ? (double) presentes / asistenciasTotal * 100 : 0);
        
        return ResponseEntity.ok(estadisticas);
    }
    
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> getResumen() {
        Map<String, Object> resumen = new HashMap<>();
        
        resumen.put("totalEstudiantes", estudianteRepository.count());
        resumen.put("totalRutas", rutaRepository.count());
        resumen.put("totalUsuarios", usuarioRepository.count());
        resumen.put("asistenciasHoy", asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.presente, LocalDate.now()) +
                                     asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.ausente, LocalDate.now()));
        
        return ResponseEntity.ok(resumen);
    }
}
