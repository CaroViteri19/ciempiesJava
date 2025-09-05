package com.ciempies.sgi.controller;

import com.ciempies.sgi.entity.Asistencia;
import com.ciempies.sgi.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaController {


    @Autowired
    private AsistenciaRepository asistenciaRepository;
    
    @GetMapping
    public ResponseEntity<List<Asistencia>> getAllAsistencias() {
        return ResponseEntity.ok(asistenciaRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> getAsistenciaById(@PathVariable Integer id) {
        Optional<Asistencia> asistencia = asistenciaRepository.findById(id);
        return asistencia.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/ruta/{codRuta}")
    public ResponseEntity<List<Asistencia>> getAsistenciasByRuta(@PathVariable Integer codRuta) {
        List<Asistencia> asistencias = asistenciaRepository.findByCodRuta(codRuta);
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/estudiante/{nombreEstudiante}")
    public ResponseEntity<List<Asistencia>> getAsistenciasByEstudiante(@PathVariable String nombreEstudiante) {
        List<Asistencia> asistencias = asistenciaRepository.findByNombreEstudiante(nombreEstudiante);
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Asistencia>> getAsistenciasByEstado(@PathVariable Asistencia.Estado estado) {
        List<Asistencia> asistencias = asistenciaRepository.findByEstado(estado);
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Asistencia>> getAsistenciasByFecha(@PathVariable String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        List<Asistencia> asistencias = asistenciaRepository.findByFecha(localDate);
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Asistencia>> getAsistenciasByUsuario(@PathVariable Integer idUsuario) {
        List<Asistencia> asistencias = asistenciaRepository.findByIdUsuario(idUsuario);
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Asistencia>> getAsistenciasByRangoFechas(
            @RequestParam String fechaInicio, 
            @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        List<Asistencia> asistencias = asistenciaRepository.findByFechaBetween(inicio, fin);
        return ResponseEntity.ok(asistencias);
    }
    
    @PostMapping
    public ResponseEntity<?> createAsistencia(@RequestBody Asistencia asistencia) {
        if (asistencia.getFecha() == null) {
            asistencia.setFecha(LocalDate.now());
        }
        
        if (asistencia.getHoraInicio() == null) {
            asistencia.setHoraInicio(LocalTime.now());
        }
        
        if (asistencia.getHoraFinal() == null) {
            asistencia.setHoraFinal(LocalTime.now());
        }
        
        Asistencia savedAsistencia = asistenciaRepository.save(asistencia);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Asistencia registrada exitosamente");
        response.put("asistencia", savedAsistencia);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsistencia(@PathVariable Integer id, @RequestBody Asistencia asistencia) {
        Optional<Asistencia> existingAsistencia = asistenciaRepository.findById(id);
        if (existingAsistencia.isPresent()) {
            asistencia.setIdAsistencia(id);
            Asistencia updatedAsistencia = asistenciaRepository.save(asistencia);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Asistencia actualizada exitosamente");
            response.put("asistencia", updatedAsistencia);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAsistencia(@PathVariable Integer id) {
        if (asistenciaRepository.existsById(id)) {
            asistenciaRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Asistencia eliminada exitosamente");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/estadisticas/fecha/{fecha}")
    public ResponseEntity<Map<String, Object>> getEstadisticasByFecha(@PathVariable String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalAsistencias = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.presente, localDate) +
                               asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.ausente, localDate);
        long presentes = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.presente, localDate);
        long ausentes = asistenciaRepository.countByEstadoAndFecha(Asistencia.Estado.ausente, localDate);
        
        estadisticas.put("fecha", fecha);
        estadisticas.put("totalAsistencias", totalAsistencias);
        estadisticas.put("presentes", presentes);
        estadisticas.put("ausentes", ausentes);
        estadisticas.put("porcentajeAsistencia", totalAsistencias > 0 ? (double) presentes / totalAsistencias * 100 : 0);
        
        return ResponseEntity.ok(estadisticas);
    }
}


