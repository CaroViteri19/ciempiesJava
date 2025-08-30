package com.ciempies.sgi.controller;

import com.ciempies.sgi.entity.Ruta;
import com.ciempies.sgi.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rutas")
@CrossOrigin(origins = "*")
public class RutaController {
    
    @Autowired
    private RutaRepository rutaRepository;
    
    @GetMapping
    public ResponseEntity<List<Ruta>> getAllRutas() {
        return ResponseEntity.ok(rutaRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ruta> getRutaById(@PathVariable Integer id) {
        Optional<Ruta> ruta = rutaRepository.findById(id);
        return ruta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codigo/{codRuta}")
    public ResponseEntity<Ruta> getRutaByCodRuta(@PathVariable Integer codRuta) {
        Optional<Ruta> ruta = rutaRepository.findByCodRuta(codRuta);
        return ruta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Ruta>> getRutasByUsuario(@PathVariable Integer idUsuario) {
        List<Ruta> rutas = rutaRepository.findByIdUsuario(idUsuario);
        return ResponseEntity.ok(rutas);
    }
    
    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<Ruta>> getRutasByEstudiante(@PathVariable Integer idEstudiante) {
        List<Ruta> rutas = rutaRepository.findByIdEstudiante(idEstudiante);
        return ResponseEntity.ok(rutas);
    }
    
    @GetMapping("/inscripcion/{idInscripcion}")
    public ResponseEntity<List<Ruta>> getRutasByInscripcion(@PathVariable Integer idInscripcion) {
        List<Ruta> rutas = rutaRepository.findByIdInscripcion(idInscripcion);
        return ResponseEntity.ok(rutas);
    }
    
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Ruta>> buscarRutas(@PathVariable String nombre) {
        List<Ruta> rutas = rutaRepository.findByNombreRutaContaining(nombre);
        return ResponseEntity.ok(rutas);
    }
    
    @PostMapping
    public ResponseEntity<?> createRuta(@RequestBody Ruta ruta) {
        if (rutaRepository.existsByCodRuta(ruta.getCodRuta())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Ya existe una ruta con ese código");
            return ResponseEntity.badRequest().body(response);
        }
        
        Ruta savedRuta = rutaRepository.save(ruta);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Ruta creada exitosamente");
        response.put("ruta", savedRuta);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRuta(@PathVariable Integer id, @RequestBody Ruta ruta) {
        Optional<Ruta> existingRuta = rutaRepository.findById(id);
        if (existingRuta.isPresent()) {
            ruta.setIdRuta(id);
            Ruta updatedRuta = rutaRepository.save(ruta);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Ruta actualizada exitosamente");
            response.put("ruta", updatedRuta);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRuta(@PathVariable Integer id) {
        if (rutaRepository.existsById(id)) {
            rutaRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Ruta eliminada exitosamente");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/estadisticas/usuario/{idUsuario}")
    public ResponseEntity<Map<String, Object>> getEstadisticasByUsuario(@PathVariable Integer idUsuario) {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalRutas = rutaRepository.countByIdUsuario(idUsuario);
        List<Ruta> rutas = rutaRepository.findByIdUsuario(idUsuario);
        
        estadisticas.put("idUsuario", idUsuario);
        estadisticas.put("totalRutas", totalRutas);
        estadisticas.put("rutas", rutas);
        
        return ResponseEntity.ok(estadisticas);
    }
}


