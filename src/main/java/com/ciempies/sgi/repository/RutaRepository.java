package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    
    Optional<Ruta> findByCodRuta(Integer codRuta);
    
    List<Ruta> findByIdUsuario(Integer idUsuario);
    
    List<Ruta> findByIdEstudiante(Integer idEstudiante);
    
    List<Ruta> findByIdInscripcion(Integer idInscripcion);
    
    @Query("SELECT r FROM Ruta r WHERE r.nombreRuta LIKE %:nombre%")
    List<Ruta> findByNombreRutaContaining(@Param("nombre") String nombre);
    
    boolean existsByCodRuta(Integer codRuta);
    
    @Query("SELECT COUNT(r) FROM Ruta r WHERE r.idUsuario = :idUsuario")
    long countByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
