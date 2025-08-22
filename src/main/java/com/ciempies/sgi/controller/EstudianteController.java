package com.ciempies.sgi.controller;

import com.ciempies.sgi.dto.ApiResponse;
import com.ciempies.sgi.entity.Estudiante;
import com.ciempies.sgi.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiantes")
@Tag(name = "Estudiantes", description = "Endpoints para la gestión de estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los estudiantes", description = "Retorna una lista paginada de estudiantes")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL', 'MONITOR')")
    public ResponseEntity<ApiResponse<Page<Estudiante>>> getAllEstudiantes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Estudiante> estudiantes = estudianteService.findAllPaged(pageable);
            return ResponseEntity.ok(ApiResponse.success("Estudiantes obtenidos exitosamente", estudiantes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estudiantes: " + e.getMessage()));
        }
    }
    
    @GetMapping("/all")
    @Operation(summary = "Obtener todos los estudiantes sin paginación", description = "Retorna todos los estudiantes activos")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL')")
    public ResponseEntity<ApiResponse<List<Estudiante>>> getAllEstudiantesList() {
        try {
            List<Estudiante> estudiantes = estudianteService.findAll();
            return ResponseEntity.ok(ApiResponse.success("Estudiantes obtenidos exitosamente", estudiantes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estudiantes: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener estudiante por ID", description = "Retorna un estudiante específico por su ID")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL', 'MONITOR')")
    public ResponseEntity<ApiResponse<Estudiante>> getEstudianteById(@PathVariable Long id) {
        try {
            Optional<Estudiante> estudiante = estudianteService.findById(id);
            if (estudiante.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Estudiante encontrado", estudiante.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estudiante: " + e.getMessage()));
        }
    }
    
    @GetMapping("/documento/{documento}")
    @Operation(summary = "Obtener estudiante por documento", description = "Retorna un estudiante específico por su documento")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL', 'MONITOR')")
    public ResponseEntity<ApiResponse<Estudiante>> getEstudianteByDocumento(@PathVariable String documento) {
        try {
            Optional<Estudiante> estudiante = estudianteService.findByDocumento(documento);
            if (estudiante.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Estudiante encontrado", estudiante.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estudiante: " + e.getMessage()));
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear estudiante", description = "Crea un nuevo estudiante")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL')")
    public ResponseEntity<ApiResponse<Estudiante>> createEstudiante(@Valid @RequestBody Estudiante estudiante) {
        try {
            if (estudianteService.existsByDocumento(estudiante.getDocumento())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe un estudiante con ese documento"));
            }
            
            Estudiante nuevoEstudiante = estudianteService.save(estudiante);
            return ResponseEntity.ok(ApiResponse.success("Estudiante creado exitosamente", nuevoEstudiante));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al crear estudiante: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estudiante", description = "Actualiza un estudiante existente")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL')")
    public ResponseEntity<ApiResponse<Estudiante>> updateEstudiante(@PathVariable Long id, @Valid @RequestBody Estudiante estudiante) {
        try {
            Estudiante estudianteActualizado = estudianteService.update(id, estudiante);
            return ResponseEntity.ok(ApiResponse.success("Estudiante actualizado exitosamente", estudianteActualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al actualizar estudiante: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estudiante", description = "Elimina un estudiante (marca como inactivo)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<String>> deleteEstudiante(@PathVariable Long id) {
        try {
            estudianteService.delete(id);
            return ResponseEntity.ok(ApiResponse.success("Estudiante eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al eliminar estudiante: " + e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Buscar estudiantes", description = "Busca estudiantes por nombre, apellido o documento")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL', 'MONITOR')")
    public ResponseEntity<ApiResponse<Page<Estudiante>>> searchEstudiantes(
            @RequestParam String busqueda,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Estudiante> estudiantes = estudianteService.search(busqueda, pageable);
            return ResponseEntity.ok(ApiResponse.success("Búsqueda completada", estudiantes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error en la búsqueda: " + e.getMessage()));
        }
    }
    
    @GetMapping("/ruta/{idRuta}")
    @Operation(summary = "Obtener estudiantes por ruta", description = "Retorna todos los estudiantes de una ruta específica")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL', 'MONITOR')")
    public ResponseEntity<ApiResponse<List<Estudiante>>> getEstudiantesByRuta(@PathVariable Long idRuta) {
        try {
            List<Estudiante> estudiantes = estudianteService.findByRuta(idRuta);
            return ResponseEntity.ok(ApiResponse.success("Estudiantes de la ruta obtenidos", estudiantes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estudiantes de la ruta: " + e.getMessage()));
        }
    }
    
    @GetMapping("/curso/{curso}")
    @Operation(summary = "Obtener estudiantes por curso", description = "Retorna todos los estudiantes de un curso específico")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL')")
    public ResponseEntity<ApiResponse<List<Estudiante>>> getEstudiantesByCurso(@PathVariable String curso) {
        try {
            List<Estudiante> estudiantes = estudianteService.findByCurso(curso);
            return ResponseEntity.ok(ApiResponse.success("Estudiantes del curso obtenidos", estudiantes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estudiantes del curso: " + e.getMessage()));
        }
    }
    
    @PostMapping("/{idEstudiante}/asignar-ruta/{idRuta}")
    @Operation(summary = "Asignar ruta a estudiante", description = "Asigna una ruta específica a un estudiante")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL')")
    public ResponseEntity<ApiResponse<Estudiante>> asignarRuta(
            @PathVariable Long idEstudiante, @PathVariable Long idRuta) {
        try {
            Estudiante estudiante = estudianteService.asignarRuta(idEstudiante, idRuta);
            return ResponseEntity.ok(ApiResponse.success("Ruta asignada exitosamente", estudiante));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al asignar ruta: " + e.getMessage()));
        }
    }
    
    @GetMapping("/estadisticas/sexo")
    @Operation(summary = "Estadísticas por sexo", description = "Retorna estadísticas de estudiantes por sexo")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL')")
    public ResponseEntity<ApiResponse<List<Object[]>>> getEstadisticasPorSexo() {
        try {
            List<Object[]> estadisticas = estudianteService.getEstadisticasPorSexo();
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas", estadisticas));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }
    
    @GetMapping("/estadisticas/curso")
    @Operation(summary = "Estadísticas por curso", description = "Retorna estadísticas de estudiantes por curso")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'APOYO_TERRITORIAL')")
    public ResponseEntity<ApiResponse<List<Object[]>>> getEstadisticasPorCurso() {
        try {
            List<Object[]> estadisticas = estudianteService.getEstadisticasPorCurso();
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas", estadisticas));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }
}
