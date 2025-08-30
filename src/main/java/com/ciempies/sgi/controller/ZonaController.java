package com.ciempies.sgi.controller;

import com.ciempies.sgi.entity.Zona;
import com.ciempies.sgi.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/zonas")
@CrossOrigin(origins = "*")
public class ZonaController {
    
    @Autowired
    private ZonaRepository zonaRepository;
    
    @GetMapping
    public ResponseEntity<List<Zona>> getAllZonas() {
        return ResponseEntity.ok(zonaRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Zona> getZonaById(@PathVariable Integer id) {
        Optional<Zona> zona = zonaRepository.findById(id);
        return zona.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/colegio/{idColegio}")
    public ResponseEntity<List<Zona>> getZonasByColegio(@PathVariable Integer idColegio) {
        List<Zona> zonas = zonaRepository.findByIdColegio(idColegio);
        return ResponseEntity.ok(zonas);
    }
    
    @GetMapping("/codigo/{codZona}")
    public ResponseEntity<List<Zona>> getZonasByCodZona(@PathVariable Integer codZona) {
        List<Zona> zonas = zonaRepository.findByCodZona(codZona);
        return ResponseEntity.ok(zonas);
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Zona>> getZonasByUsuario(@PathVariable Integer idUsuario) {
        List<Zona> zonas = zonaRepository.findByIdUsuario(idUsuario);
        return ResponseEntity.ok(zonas);
    }
    
    @PostMapping
    public ResponseEntity<?> createZona(@RequestBody Zona zona) {
        Zona savedZona = zonaRepository.save(zona);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Zona creada exitosamente");
        response.put("zona", savedZona);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateZona(@PathVariable Integer id, @RequestBody Zona zona) {
        Optional<Zona> existingZona = zonaRepository.findById(id);
        if (existingZona.isPresent()) {
            zona.setIdZona(id);
            Zona updatedZona = zonaRepository.save(zona);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Zona actualizada exitosamente");
            response.put("zona", updatedZona);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteZona(@PathVariable Integer id) {
        if (zonaRepository.existsById(id)) {
            zonaRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Zona eliminada exitosamente");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/estadisticas/colegio/{idColegio}")
    public ResponseEntity<Map<String, Object>> getEstadisticasByColegio(@PathVariable Integer idColegio) {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalZonas = zonaRepository.countByIdColegio(idColegio);
        List<Zona> zonas = zonaRepository.findByIdColegio(idColegio);
        
        estadisticas.put("idColegio", idColegio);
        estadisticas.put("totalZonas", totalZonas);
        estadisticas.put("zonas", zonas);
        
        return ResponseEntity.ok(estadisticas);
    }
    
    @GetMapping("/estadisticas/usuario/{idUsuario}")
    public ResponseEntity<Map<String, Object>> getEstadisticasByUsuario(@PathVariable Integer idUsuario) {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalZonas = zonaRepository.countByIdUsuario(idUsuario);
        List<Zona> zonas = zonaRepository.findByIdUsuario(idUsuario);
        
        estadisticas.put("idUsuario", idUsuario);
        estadisticas.put("totalZonas", totalZonas);
        estadisticas.put("zonas", zonas);
        
        return ResponseEntity.ok(estadisticas);
    }
}


