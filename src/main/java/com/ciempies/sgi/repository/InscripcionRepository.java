package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    
    List<Inscripcion> findByCodRuta(Integer codRuta);
    
    List<Inscripcion> findByNombreEstudiante(String nombreEstudiante);
    
    List<Inscripcion> findByDocumento(String documento);
    
    List<Inscripcion> findByEstado(Inscripcion.Estado estado);
    
    List<Inscripcion> findByFechaInscripcion(LocalDate fechaInscripcion);
    
    List<Inscripcion> findByIdRuta(Integer idRuta);
    
    @Query("SELECT i FROM Inscripcion i WHERE i.fechaInscripcion BETWEEN :fechaInicio AND :fechaFin")
    List<Inscripcion> findByFechaInscripcionBetween(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT i FROM Inscripcion i WHERE i.codRuta = :codRuta AND i.estado = :estado")
    List<Inscripcion> findByCodRutaAndEstado(@Param("codRuta") Integer codRuta, @Param("estado") Inscripcion.Estado estado);
    
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.estado = :estado")
    long countByEstado(@Param("estado") Inscripcion.Estado estado);
    
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.codRuta = :codRuta AND i.estado = :estado")
    long countByCodRutaAndEstado(@Param("codRuta") Integer codRuta, @Param("estado") Inscripcion.Estado estado);
}

