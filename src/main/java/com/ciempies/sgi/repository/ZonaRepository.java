package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer> {
    
    List<Zona> findByIdColegio(Integer idColegio);
    
    List<Zona> findByCodZona(Integer codZona);
    
    List<Zona> findByIdUsuario(Integer idUsuario);
    
    @Query("SELECT COUNT(z) FROM Zona z WHERE z.idColegio = :idColegio")
    long countByIdColegio(@Param("idColegio") Integer idColegio);
    
    @Query("SELECT COUNT(z) FROM Zona z WHERE z.idUsuario = :idUsuario")
    long countByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
