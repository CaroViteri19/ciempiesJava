package com.ciempies.sgi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ruta")
public class Ruta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Ruta")
    private Integer idRuta;
    
    @Column(name = "Id_Estudiante", nullable = false)
    private Integer idEstudiante;
    
    @Column(name = "Id_Usuario", nullable = false)
    private Integer idUsuario;
    
    @Column(name = "Cod_Ruta", nullable = false)
    private Integer codRuta;
    
    @Column(name = "Id_Inscripcion")
    private Integer idInscripcion;
    
    @Column(name = "Nombre_ruta", length = 25)
    private String nombreRuta;
    
    // Constructores
    public Ruta() {}
    
    public Ruta(Integer idEstudiante, Integer idUsuario, Integer codRuta, String nombreRuta) {
        this.idEstudiante = idEstudiante;
        this.idUsuario = idUsuario;
        this.codRuta = codRuta;
        this.nombreRuta = nombreRuta;
    }
    
    // Getters y Setters
    public Integer getIdRuta() {
        return idRuta;
    }
    
    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }
    
    public Integer getIdEstudiante() {
        return idEstudiante;
    }
    
    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Integer getCodRuta() {
        return codRuta;
    }
    
    public void setCodRuta(Integer codRuta) {
        this.codRuta = codRuta;
    }
    
    public Integer getIdInscripcion() {
        return idInscripcion;
    }
    
    public void setIdInscripcion(Integer idInscripcion) {
        this.idInscripcion = idInscripcion;
    }
    
    public String getNombreRuta() {
        return nombreRuta;
    }
    
    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
}
