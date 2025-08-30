package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByEmailAndContraseña(String email, String contraseña);
    
    List<Usuario> findByRol(String rol);
    
    List<Usuario> findByIdZona(Integer idZona);
    
    List<Usuario> findByIdRuta(Integer idRuta);
    
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre% OR u.apellido LIKE %:nombre%")
    List<Usuario> findByNombreContaining(@Param("nombre") String nombre);
    
    boolean existsByEmail(String email);
}
