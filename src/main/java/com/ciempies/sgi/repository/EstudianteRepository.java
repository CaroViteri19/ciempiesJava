package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Estudiante;
import com.ciempies.sgi.entity.Ruta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    Optional<Estudiante> findByDocumento(String documento);
    
    Optional<Estudiante> findByDocumentoAndActivoTrue(String documento);
    
    boolean existsByDocumento(String documento);
    
    List<Estudiante> findByRuta(Ruta ruta);
    
    List<Estudiante> findByRutaAndActivoTrue(Ruta ruta);
    
    List<Estudiante> findByActivoTrue();
    
    Page<Estudiante> findByActivoTrue(Pageable pageable);
    
    @Query("SELECT e FROM Estudiante e WHERE e.activo = true AND " +
           "(LOWER(e.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(e.apellido) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "e.documento LIKE CONCAT('%', :busqueda, '%'))")
    Page<Estudiante> buscarEstudiantes(@Param("busqueda") String busqueda, Pageable pageable);
    
    @Query("SELECT e FROM Estudiante e WHERE e.ruta.idRuta = :idRuta AND e.activo = true")
    List<Estudiante> findByRutaId(@Param("idRuta") Long idRuta);
    
    @Query("SELECT COUNT(e) FROM Estudiante e WHERE e.ruta.idRuta = :idRuta AND e.activo = true")
    long countByRutaId(@Param("idRuta") Long idRuta);
    
    @Query("SELECT e FROM Estudiante e WHERE e.curso = :curso AND e.activo = true")
    List<Estudiante> findByCurso(@Param("curso") String curso);
    
    @Query("SELECT e FROM Estudiante e WHERE e.edad BETWEEN :edadMin AND :edadMax AND e.activo = true")
    List<Estudiante> findByRangoEdad(@Param("edadMin") Integer edadMin, @Param("edadMax") Integer edadMax);
    
    @Query("SELECT e.sexo, COUNT(e) FROM Estudiante e WHERE e.activo = true GROUP BY e.sexo")
    List<Object[]> contarPorSexo();
    
    @Query("SELECT e.curso, COUNT(e) FROM Estudiante e WHERE e.activo = true GROUP BY e.curso ORDER BY e.curso")
    List<Object[]> contarPorCurso();
    
    // Métodos para el dashboard
    List<Estudiante> findByCursoAndActivoTrue(Integer curso);
    
    List<Estudiante> findByEdadBetweenAndActivoTrue(Integer edadMin, Integer edadMax);
    
    long countBySexoAndActivoTrue(String sexo);
    
    long countByCursoAndActivoTrue(Integer curso);
    
    long countByActivoTrue();
    
    @Query("SELECT e FROM Estudiante e WHERE e.activo = true")
    List<Estudiante> findAllActivos();
}
