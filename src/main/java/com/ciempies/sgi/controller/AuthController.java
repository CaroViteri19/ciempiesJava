package com.ciempies.sgi.controller;

import com.ciempies.sgi.dto.ApiResponse;
import com.ciempies.sgi.dto.LoginRequest;
import com.ciempies.sgi.dto.LoginResponse;
import com.ciempies.sgi.entity.Usuario;
import com.ciempies.sgi.security.JwtTokenProvider;
import com.ciempies.sgi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación y autorización")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y retorna un token JWT")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getCorreo(),
                    loginRequest.getContraseña()
                )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            String jwt = tokenProvider.generateToken(authentication);
            Usuario usuario = (Usuario) authentication.getPrincipal();
            
            // Actualizar último acceso
            usuarioService.updateLastAccess(usuario.getId());
            
            LoginResponse loginResponse = new LoginResponse(jwt, usuario);
            
            return ResponseEntity.ok(ApiResponse.success("Login exitoso", loginResponse));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Credenciales inválidas"));
        }
    }
    
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    public ResponseEntity<ApiResponse<Usuario>> register(@Valid @RequestBody Usuario usuario) {
        try {
            if (usuarioService.existsByCorreo(usuario.getCorreo())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El correo ya está registrado"));
            }
            
            Usuario nuevoUsuario = usuarioService.save(usuario);
            return ResponseEntity.ok(ApiResponse.success("Usuario registrado exitosamente", nuevoUsuario));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al registrar usuario: " + e.getMessage()));
        }
    }
    
    @GetMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida si un token JWT es válido")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestHeader("Authorization") String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                if (tokenProvider.validateToken(jwt)) {
                    String username = tokenProvider.getUsernameFromToken(jwt);
                    return ResponseEntity.ok(ApiResponse.success("Token válido", username));
                }
            }
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Token inválido"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al validar token"));
        }
    }
    
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Cierra la sesión del usuario")
    public ResponseEntity<ApiResponse<String>> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(ApiResponse.success("Sesión cerrada exitosamente"));
    }
}
