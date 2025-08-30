package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    
    Optional<Estudiante> findByDocumento(Integer documento);
    
    List<Estudiante> findByIdRuta(Integer idRuta);
    
    List<Estudiante> findByIdColegio(Integer idColegio);
    
    List<Estudiante> findByCurso(String curso);
    
    List<Estudiante> findBySexo(Estudiante.Sexo sexo);
    
    @Query("SELECT e FROM Estudiante e WHERE e.nombre LIKE %:nombre% OR e.apellido LIKE %:nombre%")
    List<Estudiante> findByNombreContaining(@Param("nombre") String nombre);
    
    @Query("SELECT e FROM Estudiante e WHERE e.edad BETWEEN :edadMin AND :edadMax")
    List<Estudiante> findByEdadBetween(@Param("edadMin") Integer edadMin, @Param("edadMax") Integer edadMax);
    
    List<Estudiante> findByEps(String eps);
    
    List<Estudiante> findByDiscapacidad(String discapacidad);
    
    List<Estudiante> findByEtnia(String etnia);
    
    boolean existsByDocumento(Integer documento);
    
    @Query("SELECT COUNT(e) FROM Estudiante e WHERE e.idRuta = :idRuta")
    long countByIdRuta(@Param("idRuta") Integer idRuta);
    
    @Query("SELECT COUNT(e) FROM Estudiante e WHERE e.idColegio = :idColegio")
    long countByIdColegio(@Param("idColegio") Integer idColegio);
    
    long countBySexo(Estudiante.Sexo sexo);
}
