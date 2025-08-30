package com.ciempies.sgi.controller;

import com.ciempies.sgi.entity.Inscripcion;
import com.ciempies.sgi.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
public class InscripcionController {
    
    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    @GetMapping
    public ResponseEntity<List<Inscripcion>> getAllInscripciones() {
        return ResponseEntity.ok(inscripcionRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> getInscripcionById(@PathVariable Integer id) {
        Optional<Inscripcion> inscripcion = inscripcionRepository.findById(id);
        return inscripcion.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/ruta/{codRuta}")
    public ResponseEntity<List<Inscripcion>> getInscripcionesByRuta(@PathVariable Integer codRuta) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByCodRuta(codRuta);
        return ResponseEntity.ok(inscripciones);
    }
    
    @GetMapping("/estudiante/{nombreEstudiante}")
    public ResponseEntity<List<Inscripcion>> getInscripcionesByEstudiante(@PathVariable String nombreEstudiante) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByNombreEstudiante(nombreEstudiante);
        return ResponseEntity.ok(inscripciones);
    }
    
    @GetMapping("/documento/{documento}")
    public ResponseEntity<List<Inscripcion>> getInscripcionesByDocumento(@PathVariable String documento) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByDocumento(documento);
        return ResponseEntity.ok(inscripciones);
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Inscripcion>> getInscripcionesByEstado(@PathVariable Inscripcion.Estado estado) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByEstado(estado);
        return ResponseEntity.ok(inscripciones);
    }
    
    @GetMapping("/fecha/{fechaInscripcion}")
    public ResponseEntity<List<Inscripcion>> getInscripcionesByFecha(@PathVariable String fechaInscripcion) {
        LocalDate fecha = LocalDate.parse(fechaInscripcion);
        List<Inscripcion> inscripciones = inscripcionRepository.findByFechaInscripcion(fecha);
        return ResponseEntity.ok(inscripciones);
    }
    
    @GetMapping("/ruta-estado")
    public ResponseEntity<List<Inscripcion>> getInscripcionesByRutaAndEstado(
            @RequestParam Integer codRuta, 
            @RequestParam Inscripcion.Estado estado) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByCodRutaAndEstado(codRuta, estado);
        return ResponseEntity.ok(inscripciones);
    }
    
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Inscripcion>> getInscripcionesByRangoFechas(
            @RequestParam String fechaInicio, 
            @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        List<Inscripcion> inscripciones = inscripcionRepository.findByFechaInscripcionBetween(inicio, fin);
        return ResponseEntity.ok(inscripciones);
    }
    
    @PostMapping
    public ResponseEntity<?> createInscripcion(@RequestBody Inscripcion inscripcion) {
        if (inscripcion.getFechaInscripcion() == null) {
            inscripcion.setFechaInscripcion(LocalDate.now());
        }
        
        Inscripcion savedInscripcion = inscripcionRepository.save(inscripcion);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Inscripción creada exitosamente");
        response.put("inscripcion", savedInscripcion);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInscripcion(@PathVariable Integer id, @RequestBody Inscripcion inscripcion) {
        Optional<Inscripcion> existingInscripcion = inscripcionRepository.findById(id);
        if (existingInscripcion.isPresent()) {
            inscripcion.setIdInscripcion(id);
            Inscripcion updatedInscripcion = inscripcionRepository.save(inscripcion);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Inscripción actualizada exitosamente");
            response.put("inscripcion", updatedInscripcion);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInscripcion(@PathVariable Integer id) {
        if (inscripcionRepository.existsById(id)) {
            inscripcionRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Inscripción eliminada exitosamente");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/estadisticas/estado")
    public ResponseEntity<Map<String, Object>> getEstadisticasByEstado() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalInscripciones = inscripcionRepository.count();
        long activas = inscripcionRepository.countByEstado(Inscripcion.Estado.activo);
        long pendientes = inscripcionRepository.countByEstado(Inscripcion.Estado.pendiente);
        long rechazadas = inscripcionRepository.countByEstado(Inscripcion.Estado.rechazado);
        
        estadisticas.put("totalInscripciones", totalInscripciones);
        estadisticas.put("activas", activas);
        estadisticas.put("pendientes", pendientes);
        estadisticas.put("rechazadas", rechazadas);
        estadisticas.put("porcentajeAprobacion", totalInscripciones > 0 ? (double) activas / totalInscripciones * 100 : 0);
        
        return ResponseEntity.ok(estadisticas);
    }
}


