package com.ciempies.sgi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "zona")
public class Zona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Zona")
    private Integer idZona;
    
    @Column(name = "Id_Colegio", nullable = false)
    private Integer idColegio;
    
    @Column(name = "Cod_Zona", nullable = false)
    private Integer codZona;
    
    @Column(name = "Id_Usuario")
    private Integer idUsuario;
    
    // Constructores
    public Zona() {}
    
    public Zona(Integer idColegio, Integer codZona, Integer idUsuario) {
        this.idColegio = idColegio;
        this.codZona = codZona;
        this.idUsuario = idUsuario;
    }
    
    // Getters y Setters
    public Integer getIdZona() {
        return idZona;
    }
    
    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }
    
    public Integer getIdColegio() {
        return idColegio;
    }
    
    public void setIdColegio(Integer idColegio) {
        this.idColegio = idColegio;
    }
    
    public Integer getCodZona() {
        return codZona;
    }
    
    public void setCodZona(Integer codZona) {
        this.codZona = codZona;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}

