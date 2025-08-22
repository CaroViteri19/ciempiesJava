package com.ciempies.sgi.repository;

import com.ciempies.sgi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByCorreo(String correo);
    
    Optional<Usuario> findByCorreoAndActivoTrue(String correo);
    
    boolean existsByCorreo(String correo);
    
    List<Usuario> findByRol(Usuario.Rol rol);
    
    List<Usuario> findByActivoTrue();
    
    @Query("SELECT u FROM Usuario u WHERE u.rol IN :roles AND u.activo = true")
    List<Usuario> findByRolesAndActivo(@Param("roles") List<Usuario.Rol> roles);
    
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol AND u.activo = true")
    long countByRolAndActivo(@Param("rol") Usuario.Rol rol);
    
    // Métodos para el dashboard
    long countByActivoTrue();
}
