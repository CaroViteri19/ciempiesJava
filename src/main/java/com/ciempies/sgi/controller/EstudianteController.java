package com.ciempies.sgi.controller;

import com.ciempies.sgi.entity.Estudiante;
import com.ciempies.sgi.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @GetMapping
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        return ResponseEntity.ok(estudianteRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Integer id) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(id);
        return estudiante.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/documento/{documento}")
    public ResponseEntity<Estudiante> getEstudianteByDocumento(@PathVariable Integer documento) {
        Optional<Estudiante> estudiante = estudianteRepository.findByDocumento(documento);
        return estudiante.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/ruta/{idRuta}")
    public ResponseEntity<List<Estudiante>> getEstudiantesByRuta(@PathVariable Integer idRuta) {
        List<Estudiante> estudiantes = estudianteRepository.findByIdRuta(idRuta);
        return ResponseEntity.ok(estudiantes);
    }
    
    @GetMapping("/colegio/{idColegio}")
    public ResponseEntity<List<Estudiante>> getEstudiantesByColegio(@PathVariable Integer idColegio) {
        List<Estudiante> estudiantes = estudianteRepository.findByIdColegio(idColegio);
        return ResponseEntity.ok(estudiantes);
    }
    
    @GetMapping("/curso/{curso}")
    public ResponseEntity<List<Estudiante>> getEstudiantesByCurso(@PathVariable String curso) {
        List<Estudiante> estudiantes = estudianteRepository.findByCurso(curso);
        return ResponseEntity.ok(estudiantes);
    }
    
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Estudiante>> buscarEstudiantes(@PathVariable String nombre) {
        List<Estudiante> estudiantes = estudianteRepository.findByNombreContaining(nombre);
        return ResponseEntity.ok(estudiantes);
    }
    
    @PostMapping
    public ResponseEntity<?> createEstudiante(@RequestBody Estudiante estudiante) {
        if (estudianteRepository.existsByDocumento(estudiante.getDocumento())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Ya existe un estudiante con ese documento");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (estudiante.getFechaInscripcion() == null) {
            estudiante.setFechaInscripcion(LocalDate.now());
        }
        
        Estudiante savedEstudiante = estudianteRepository.save(estudiante);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Estudiante creado exitosamente");
        response.put("estudiante", savedEstudiante);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEstudiante(@PathVariable Integer id, @RequestBody Estudiante estudiante) {
        Optional<Estudiante> existingEstudiante = estudianteRepository.findById(id);
        if (existingEstudiante.isPresent()) {
            estudiante.setIdEstudiante(id);
            Estudiante updatedEstudiante = estudianteRepository.save(estudiante);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Estudiante actualizado exitosamente");
            response.put("estudiante", updatedEstudiante);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstudiante(@PathVariable Integer id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Estudiante eliminado exitosamente");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> getEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalEstudiantes = estudianteRepository.count();
        long estudiantesFemeninos = estudianteRepository.countBySexo(Estudiante.Sexo.femenino);
        long estudiantesMasculinos = estudianteRepository.countBySexo(Estudiante.Sexo.masculino);
        
        estadisticas.put("totalEstudiantes", totalEstudiantes);
        estadisticas.put("estudiantesFemeninos", estudiantesFemeninos);
        estadisticas.put("estudiantesMasculinos", estudiantesMasculinos);
        
        return ResponseEntity.ok(estadisticas);
    }
}
