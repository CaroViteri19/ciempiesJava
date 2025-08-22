package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    
    Optional<Ruta> findByCodRuta(String codRuta);
    
    Optional<Ruta> findByCodRutaAndActivaTrue(String codRuta);
    
    boolean existsByCodRuta(String codRuta);
    
    List<Ruta> findByActivaTrue();
    
    @Query("SELECT r FROM Ruta r WHERE r.activa = true AND r.capacidadMaxima > " +
           "(SELECT COUNT(e) FROM Estudiante e WHERE e.ruta = r AND e.activo = true)")
    List<Ruta> findRutasConCapacidad();
    
    @Query("SELECT r FROM Ruta r WHERE r.activa = true ORDER BY r.nombreRuta")
    List<Ruta> findRutasActivasOrdenadas();
    
    @Query("SELECT r FROM Ruta r WHERE LOWER(r.nombreRuta) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(r.codRuta) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Ruta> buscarRutas(@Param("busqueda") String busqueda);
    
    @Query("SELECT r, COUNT(e) as estudiantes FROM Ruta r LEFT JOIN Estudiante e ON e.ruta = r AND e.activo = true " +
           "WHERE r.activa = true GROUP BY r ORDER BY r.nombreRuta")
    List<Object[]> findRutasConConteoEstudiantes();
    
    // Métodos para el dashboard
    long countByActivaTrue();
    
    @Query("SELECT r.nombreRuta, COUNT(e) FROM Ruta r LEFT JOIN r.estudiantes e WHERE r.activa = true AND e.activo = true GROUP BY r.idRuta, r.nombreRuta ORDER BY COUNT(e) DESC")
    List<Object[]> findRutasWithStudentCount();
}
