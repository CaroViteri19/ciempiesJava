package com.ciempies.sgi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_asistencia")
    private Long idAsistencia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cod_Ruta", nullable = false)
    private Ruta ruta;
    
    @Column(name = "Nombre_ruta")
    private String nombreRuta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Estudiante", nullable = false)
    private Estudiante estudiante;
    
    @Column(name = "Nombre_Estudiante")
    private String nombreEstudiante;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false)
    private EstadoAsistencia estado;
    
    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(name = "Hora_Inicio", nullable = false)
    private LocalTime horaInicio;
    
    @NotNull(message = "La hora final es obligatoria")
    @Column(name = "Hora_Final", nullable = false)
    private LocalTime horaFinal;
    
    @Column(name = "Observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Usuario")
    private Usuario usuarioRegistro;
    
    @Column(name = "Fecha_Registro")
    private LocalDate fechaRegistro;
    
    // Constructores
    public Asistencia() {
        this.fechaRegistro = LocalDate.now();
    }
    
    public Asistencia(Ruta ruta, Estudiante estudiante, EstadoAsistencia estado, 
                     LocalDate fecha, LocalTime horaInicio, LocalTime horaFinal) {
        this();
        this.ruta = ruta;
        this.estudiante = estudiante;
        this.estado = estado;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        
        // Actualizar nombres para facilitar consultas
        if (ruta != null) {
            this.nombreRuta = ruta.getNombreRuta();
        }
        if (estudiante != null) {
            this.nombreEstudiante = estudiante.getNombreCompleto();
        }
    }
    
    // Getters y Setters
    public Long getIdAsistencia() {
        return idAsistencia;
    }
    
    public void setIdAsistencia(Long idAsistencia) {
        this.idAsistencia = idAsistencia;
    }
    
    public Ruta getRuta() {
        return ruta;
    }
    
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
        if (ruta != null) {
            this.nombreRuta = ruta.getNombreRuta();
        }
    }
    
    public String getNombreRuta() {
        return nombreRuta;
    }
    
    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
    
    public Estudiante getEstudiante() {
        return estudiante;
    }
    
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        if (estudiante != null) {
            this.nombreEstudiante = estudiante.getNombreCompleto();
        }
    }
    
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }
    
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }
    
    public EstadoAsistencia getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoAsistencia estado) {
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
    
    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }
    
    public void setUsuarioRegistro(Usuario usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }
    
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    // Métodos de utilidad
    public boolean isPresente() {
        return EstadoAsistencia.PRESENTE.equals(estado);
    }
    
    public boolean isAusente() {
        return EstadoAsistencia.AUSENTE.equals(estado);
    }
    
    // Enum para estado de asistencia
    public enum EstadoAsistencia {
        PRESENTE,
        AUSENTE
    }
}
