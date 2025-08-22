package com.ciempies.sgi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "ruta")
public class Ruta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Ruta")
    private Long idRuta;
    
    @NotBlank(message = "El código de ruta es obligatorio")
    @Size(max = 20, message = "El código de ruta no puede exceder 20 caracteres")
    @Column(name = "Cod_Ruta", unique = true, nullable = false)
    private String codRuta;
    
    @NotBlank(message = "El nombre de la ruta es obligatorio")
    @Size(max = 100, message = "El nombre de la ruta no puede exceder 100 caracteres")
    @Column(name = "Nombre_ruta", nullable = false)
    private String nombreRuta;
    
    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    @Column(name = "Descripcion")
    private String descripcion;
    
    @Column(name = "Capacidad_Maxima")
    private Integer capacidadMaxima = 30;
    
    @Column(name = "Activa")
    private Boolean activa = true;
    
    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Estudiante> estudiantes;
    
    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asistencia> asistencias;
    
    // Constructores
    public Ruta() {}
    
    public Ruta(String codRuta, String nombreRuta) {
        this.codRuta = codRuta;
        this.nombreRuta = nombreRuta;
    }
    
    public Ruta(String codRuta, String nombreRuta, String descripcion, Integer capacidadMaxima) {
        this(codRuta, nombreRuta);
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
    }
    
    // Getters y Setters
    public Long getIdRuta() {
        return idRuta;
    }
    
    public void setIdRuta(Long idRuta) {
        this.idRuta = idRuta;
    }
    
    public String getCodRuta() {
        return codRuta;
    }
    
    public void setCodRuta(String codRuta) {
        this.codRuta = codRuta;
    }
    
    public String getNombreRuta() {
        return nombreRuta;
    }
    
    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }
    
    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
    
    public Boolean getActiva() {
        return activa;
    }
    
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
    
    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }
    
    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    
    public List<Asistencia> getAsistencias() {
        return asistencias;
    }
    
    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
    
    // Métodos de utilidad
    public Integer getEstudiantesAsignados() {
        return estudiantes != null ? estudiantes.size() : 0;
    }
    
    public Boolean tieneCapacidad() {
        return getEstudiantesAsignados() < capacidadMaxima;
    }
    
    public Integer getCapacidadDisponible() {
        return capacidadMaxima - getEstudiantesAsignados();
    }
}
