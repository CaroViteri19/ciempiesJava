package com.ciempies.sgi.service;

import com.ciempies.sgi.entity.Usuario;
import com.ciempies.sgi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreoAndActivoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
    
    public List<Usuario> findAll() {
        return usuarioRepository.findByActivoTrue();
    }
    
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
    
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            // Nuevo usuario - encriptar contraseña
            usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        } else {
            // Usuario existente - verificar si la contraseña cambió
            Optional<Usuario> existingUser = usuarioRepository.findById(usuario.getId());
            if (existingUser.isPresent() && !existingUser.get().getContraseña().equals(usuario.getContraseña())) {
                usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
            }
        }
        return usuarioRepository.save(usuario);
    }
    
    public Usuario update(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioDetails.getNombre());
                    usuario.setCorreo(usuarioDetails.getCorreo());
                    usuario.setRol(usuarioDetails.getRol());
                    usuario.setActivo(usuarioDetails.getActivo());
                    
                    // Solo encriptar si la contraseña cambió
                    if (!usuario.getContraseña().equals(usuarioDetails.getContraseña())) {
                        usuario.setContraseña(passwordEncoder.encode(usuarioDetails.getContraseña()));
                    }
                    
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    public void delete(Long id) {
        usuarioRepository.findById(id)
                .ifPresent(usuario -> {
                    usuario.setActivo(false);
                    usuarioRepository.save(usuario);
                });
    }
    
    public void updateLastAccess(Long id) {
        usuarioRepository.findById(id)
                .ifPresent(usuario -> {
                    usuario.setUltimoAcceso(LocalDateTime.now());
                    usuarioRepository.save(usuario);
                });
    }
    
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }
    
    public List<Usuario> findByRol(Usuario.Rol rol) {
        return usuarioRepository.findByRol(rol);
    }
    
    public long countByRol(Usuario.Rol rol) {
        return usuarioRepository.countByRolAndActivo(rol);
    }
    
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
