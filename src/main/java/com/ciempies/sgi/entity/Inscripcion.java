package com.ciempies.sgi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Inscripcion")
    private Integer idInscripcion;
    
    @Column(name = "Cod_Ruta", nullable = false)
    private Integer codRuta;
    
    @Column(name = "Nombre_estudiante", nullable = false, length = 100)
    private String nombreEstudiante;
    
    @Column(name = "documento", nullable = false, length = 50)
    private String documento;
    
    @Column(name = "Fecha_Inscripcion", nullable = false)
    private LocalDate fechaInscripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false)
    private Estado estado;
    
    @Column(name = "Id_ruta")
    private Integer idRuta;
    
    // Constructores
    public Inscripcion() {}
    
    public Inscripcion(Integer codRuta, String nombreEstudiante, String documento, LocalDate fechaInscripcion, Estado estado) {
        this.codRuta = codRuta;
        this.nombreEstudiante = nombreEstudiante;
        this.documento = documento;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Integer getIdInscripcion() {
        return idInscripcion;
    }
    
    public void setIdInscripcion(Integer idInscripcion) {
        this.idInscripcion = idInscripcion;
    }
    
    public Integer getCodRuta() {
        return codRuta;
    }
    
    public void setCodRuta(Integer codRuta) {
        this.codRuta = codRuta;
    }
    
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }
    
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }
    
    public String getDocumento() {
        return documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }
    
    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
    
    public Estado getEstado() {
        return estado;
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Integer getIdRuta() {
        return idRuta;
    }
    
    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }
    
    // Enum para estado
    public enum Estado {
        activo, pendiente, rechazado
    }
}


