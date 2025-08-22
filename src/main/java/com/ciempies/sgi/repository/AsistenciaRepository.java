package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Asistencia;
import com.ciempies.sgi.entity.Estudiante;
import com.ciempies.sgi.entity.Ruta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    
    List<Asistencia> findByEstudiante(Estudiante estudiante);
    
    List<Asistencia> findByRuta(Ruta ruta);
    
    List<Asistencia> findByFecha(LocalDate fecha);
    
    List<Asistencia> findByRutaAndFecha(Ruta ruta, LocalDate fecha);
    
    List<Asistencia> findByEstudianteAndFecha(Estudiante estudiante, LocalDate fecha);
    
    @Query("SELECT a FROM Asistencia a WHERE a.ruta.idRuta = :idRuta AND a.fecha = :fecha")
    List<Asistencia> findByRutaIdAndFecha(@Param("idRuta") Long idRuta, @Param("fecha") LocalDate fecha);
    
    @Query("SELECT a FROM Asistencia a WHERE a.estudiante.idEstudiante = :idEstudiante AND a.fecha = :fecha")
    Optional<Asistencia> findByEstudianteIdAndFecha(@Param("idEstudiante") Long idEstudiante, @Param("fecha") LocalDate fecha);
    
    @Query("SELECT a FROM Asistencia a WHERE a.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY a.fecha DESC, a.ruta.nombreRuta")
    List<Asistencia> findByRangoFechas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT a FROM Asistencia a WHERE a.ruta.idRuta = :idRuta AND a.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY a.fecha DESC")
    List<Asistencia> findByRutaIdAndRangoFechas(@Param("idRuta") Long idRuta, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT a FROM Asistencia a WHERE a.estudiante.idEstudiante = :idEstudiante AND a.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY a.fecha DESC")
    List<Asistencia> findByEstudianteIdAndRangoFechas(@Param("idEstudiante") Long idEstudiante, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT a.estado, COUNT(a) FROM Asistencia a WHERE a.fecha = :fecha GROUP BY a.estado")
    List<Object[]> contarPorEstadoYFecha(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT a.estado, COUNT(a) FROM Asistencia a WHERE a.ruta.idRuta = :idRuta AND a.fecha = :fecha GROUP BY a.estado")
    List<Object[]> contarPorEstadoRutaYFecha(@Param("idRuta") Long idRuta, @Param("fecha") LocalDate fecha);
    
    @Query("SELECT a FROM Asistencia a WHERE a.fecha = :fecha ORDER BY a.ruta.nombreRuta, a.estudiante.nombre, a.estudiante.apellido")
    Page<Asistencia> findByFechaPaginado(@Param("fecha") LocalDate fecha, Pageable pageable);
    
    @Query("SELECT a FROM Asistencia a WHERE a.ruta.idRuta = :idRuta AND a.fecha = :fecha ORDER BY a.estudiante.nombre, a.estudiante.apellido")
    Page<Asistencia> findByRutaIdAndFechaPaginado(@Param("idRuta") Long idRuta, @Param("fecha") LocalDate fecha, Pageable pageable);
    
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.estudiante.idEstudiante = :idEstudiante AND a.estado = 'PRESENTE'")
    long contarAsistenciasPresente(@Param("idEstudiante") Long idEstudiante);
    
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.estudiante.idEstudiante = :idEstudiante AND a.estado = 'AUSENTE'")
    long contarAsistenciasAusente(@Param("idEstudiante") Long idEstudiante);
    
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.estudiante.idEstudiante = :idEstudiante")
    long contarTotalAsistencias(@Param("idEstudiante") Long idEstudiante);
    
    // Métodos para el dashboard
    long countByFecha(LocalDate fecha);
    
    long countByEstadoAndFecha(String estado, LocalDate fecha);
    
    @Query("SELECT a.ruta.nombreRuta, COUNT(a) FROM Asistencia a WHERE a.fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY a.ruta.nombreRuta")
    List<Object[]> findAsistenciasByRutaAndDateRange(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
}
