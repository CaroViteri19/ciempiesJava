package com.ciempies.sgi.dto;

import com.ciempies.sgi.entity.Usuario;

public class LoginResponse {
    
    private String token;
    private String tipoToken = "Bearer";
    private Usuario usuario;
    private String rol;
    
    // Constructores
    public LoginResponse() {}
    
    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        this.rol = usuario.getRol().name();
    }
    
    // Getters y Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getTipoToken() {
        return tipoToken;
    }
    
    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            this.rol = usuario.getRol().name();
        }
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
}
