package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    
    List<Asistencia> findByCodRuta(Integer codRuta);
    
    List<Asistencia> findByNombreEstudiante(String nombreEstudiante);
    
    List<Asistencia> findByEstado(Asistencia.Estado estado);
    
    List<Asistencia> findByFecha(LocalDate fecha);
    
    List<Asistencia> findByIdUsuario(Integer idUsuario);
    
    @Query("SELECT a FROM Asistencia a WHERE a.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Asistencia> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT a FROM Asistencia a WHERE a.codRuta = :codRuta AND a.fecha = :fecha")
    List<Asistencia> findByCodRutaAndFecha(@Param("codRuta") Integer codRuta, @Param("fecha") LocalDate fecha);
    
    @Query("SELECT a FROM Asistencia a WHERE a.nombreEstudiante = :nombreEstudiante AND a.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Asistencia> findByNombreEstudianteAndFechaBetween(@Param("nombreEstudiante") String nombreEstudiante, 
                                                          @Param("fechaInicio") LocalDate fechaInicio, 
                                                          @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.estado = :estado AND a.fecha = :fecha")
    long countByEstadoAndFecha(@Param("estado") Asistencia.Estado estado, @Param("fecha") LocalDate fecha);
    
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.codRuta = :codRuta AND a.estado = :estado AND a.fecha = :fecha")
    long countByCodRutaAndEstadoAndFecha(@Param("codRuta") Integer codRuta, 
                                        @Param("estado") Asistencia.Estado estado, 
                                        @Param("fecha") LocalDate fecha);
}
