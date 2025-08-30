package com.ciempies.sgi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Usuario")
    private Integer idUsuario;
    
    @Column(name = "Nombre", nullable = false, length = 20)
    private String nombre;
    
    @Column(name = "Apellido", nullable = false, length = 20)
    private String apellido;
    
    @Column(name = "Email", nullable = false, length = 50, unique = true)
    private String email;
    
    @Column(name = "Contraseña", nullable = false, length = 15)
    private String contraseña;
    
    @Column(name = "Rol", nullable = false, length = 50)
    private String rol;
    
    @Column(name = "Id_Zona")
    private Integer idZona;
    
    @Column(name = "Id_ruta")
    private Integer idRuta;
    
    // Constructores
    public Usuario() {}
    
    public Usuario(String nombre, String apellido, String email, String contraseña, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    
    // Getters y Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getContraseña() {
        return contraseña;
    }
    
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public Integer getIdZona() {
        return idZona;
    }
    
    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }
    
    public Integer getIdRuta() {
        return idRuta;
    }
    
    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }
}
