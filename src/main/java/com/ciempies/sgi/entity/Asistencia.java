package com.ciempies.sgi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_asistencia")
    private Integer idAsistencia;
    
    @Column(name = "Cod_Ruta", nullable = false)
    private Integer codRuta;
    
    @Column(name = "Nombre_ruta", length = 50)
    private String nombreRuta;
    
    @Column(name = "Nombre_Estudiante", length = 50)
    private String nombreEstudiante;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false)
    private Estado estado;
    
    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "Hora_Inicio", nullable = false)
    private LocalTime horaInicio;
    
    @Column(name = "Hora_Final", nullable = false)
    private LocalTime horaFinal;
    
    @Column(name = "Observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "Id_Usuario", nullable = false)
    private Integer idUsuario;
    
    // Constructores
    public Asistencia() {}
    
    public Asistencia(Integer codRuta, String nombreEstudiante, Estado estado, LocalDate fecha, 
                     LocalTime horaInicio, LocalTime horaFinal, Integer idUsuario) {
        this.codRuta = codRuta;
        this.nombreEstudiante = nombreEstudiante;
        this.estado = estado;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.idUsuario = idUsuario;
    }
    
    // Getters y Setters
    public Integer getIdAsistencia() {
        return idAsistencia;
    }
    
    public void setIdAsistencia(Integer idAsistencia) {
        this.idAsistencia = idAsistencia;
    }
    
    public Integer getCodRuta() {
        return codRuta;
    }
    
    public void setCodRuta(Integer codRuta) {
        this.codRuta = codRuta;
    }
    
    public String getNombreRuta() {
        return nombreRuta;
    }
    
    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
    
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }
    
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }
    
    public Estado getEstado() {
        return estado;
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public LocalTime getHoraFinal() {
        return horaFinal;
    }
    
    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    // Enum para estado
    public enum Estado {
        presente, ausente
    }
}
