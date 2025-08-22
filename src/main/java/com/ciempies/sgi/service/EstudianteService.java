package com.ciempies.sgi.service;

import com.ciempies.sgi.entity.Estudiante;
import com.ciempies.sgi.entity.Ruta;
import com.ciempies.sgi.repository.EstudianteRepository;
import com.ciempies.sgi.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private RutaRepository rutaRepository;
    
    public List<Estudiante> findAll() {
        return estudianteRepository.findByActivoTrue();
    }
    
    public Page<Estudiante> findAllPaged(Pageable pageable) {
        return estudianteRepository.findByActivoTrue(pageable);
    }
    
    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }
    
    public Optional<Estudiante> findByDocumento(String documento) {
        return estudianteRepository.findByDocumentoAndActivoTrue(documento);
    }
    
    public Estudiante save(Estudiante estudiante) {
        // Validar que la ruta tenga capacidad
        if (estudiante.getRuta() != null) {
            Ruta ruta = rutaRepository.findById(estudiante.getRuta().getIdRuta())
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
            
            if (!ruta.tieneCapacidad()) {
                throw new RuntimeException("La ruta " + ruta.getNombreRuta() + " no tiene capacidad disponible");
            }
        }
        
        return estudianteRepository.save(estudiante);
    }
    
    public Estudiante update(Long id, Estudiante estudianteDetails) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudiante.setNombre(estudianteDetails.getNombre());
                    estudiante.setApellido(estudianteDetails.getApellido());
                    estudiante.setDocumento(estudianteDetails.getDocumento());
                    estudiante.setSexo(estudianteDetails.getSexo());
                    estudiante.setEps(estudianteDetails.getEps());
                    estudiante.setDireccion(estudianteDetails.getDireccion());
                    estudiante.setEdad(estudianteDetails.getEdad());
                    estudiante.setDiscapacidad(estudianteDetails.getDiscapacidad());
                    estudiante.setEtnia(estudianteDetails.getEtnia());
                    estudiante.setCurso(estudianteDetails.getCurso());
                    estudiante.setTelefono(estudianteDetails.getTelefono());
                    
                    // Validar cambio de ruta
                    if (estudianteDetails.getRuta() != null && 
                        !estudianteDetails.getRuta().getIdRuta().equals(estudiante.getRuta().getIdRuta())) {
                        
                        Ruta nuevaRuta = rutaRepository.findById(estudianteDetails.getRuta().getIdRuta())
                                .orElseThrow(() -> new RuntimeException("Nueva ruta no encontrada"));
                        
                        if (!nuevaRuta.tieneCapacidad()) {
                            throw new RuntimeException("La ruta " + nuevaRuta.getNombreRuta() + " no tiene capacidad disponible");
                        }
                        
                        estudiante.setRuta(nuevaRuta);
                    }
                    
                    return estudianteRepository.save(estudiante);
                })
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }
    
    public void delete(Long id) {
        estudianteRepository.findById(id)
                .ifPresent(estudiante -> {
                    estudiante.setActivo(false);
                    estudianteRepository.save(estudiante);
                });
    }
    
    public boolean existsByDocumento(String documento) {
        return estudianteRepository.existsByDocumento(documento);
    }
    
    public Page<Estudiante> search(String busqueda, Pageable pageable) {
        return estudianteRepository.buscarEstudiantes(busqueda, pageable);
    }
    
    public List<Estudiante> findByRuta(Long idRuta) {
        return estudianteRepository.findByRutaId(idRuta);
    }
    
    public List<Estudiante> findByCurso(String curso) {
        return estudianteRepository.findByCurso(curso);
    }
    
    public List<Estudiante> findByRangoEdad(Integer edadMin, Integer edadMax) {
        return estudianteRepository.findByRangoEdad(edadMin, edadMax);
    }
    
    public long countByRuta(Long idRuta) {
        return estudianteRepository.countByRutaId(idRuta);
    }
    
    public List<Object[]> getEstadisticasPorSexo() {
        return estudianteRepository.contarPorSexo();
    }
    
    public List<Object[]> getEstadisticasPorCurso() {
        return estudianteRepository.contarPorCurso();
    }
    
    public Estudiante asignarRuta(Long idEstudiante, Long idRuta) {
        Estudiante estudiante = estudianteRepository.findById(idEstudiante)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        Ruta ruta = rutaRepository.findById(idRuta)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        
        if (!ruta.tieneCapacidad()) {
            throw new RuntimeException("La ruta " + ruta.getNombreRuta() + " no tiene capacidad disponible");
        }
        
        estudiante.setRuta(ruta);
        return estudianteRepository.save(estudiante);
    }
}
