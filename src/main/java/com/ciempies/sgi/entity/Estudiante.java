package com.ciempies.sgi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "estudiante")
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Estudiante")
    private Integer idEstudiante;
    
    @Column(name = "Id_acudiente", nullable = false)
    private Integer idAcudiente;
    
    @Column(name = "Id_colegio", nullable = false)
    private Integer idColegio;
    
    @Column(name = "Id_Jornada", nullable = false)
    private Integer idJornada;
    
    @Column(name = "Id_Ruta", nullable = false)
    private Integer idRuta;
    
    @Column(name = "Nombre", nullable = false, length = 15)
    private String nombre;
    
    @Column(name = "Apellido", nullable = false, length = 15)
    private String apellido;
    
    @Column(name = "Documento", nullable = false)
    private Integer documento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Sexo", nullable = false)
    private Sexo sexo;
    
    @Column(name = "Eps", length = 15)
    private String eps;
    
    @Column(name = "Direccion", nullable = false, length = 25)
    private String direccion;
    
    @Column(name = "Edad", nullable = false)
    private Integer edad;
    
    @Column(name = "Discapacidad", length = 15)
    private String discapacidad;
    
    @Column(name = "Etnia", length = 15)
    private String etnia;
    
    @Column(name = "Fecha_Inscripcion", nullable = false)
    private LocalDate fechaInscripcion;
    
    @Column(name = "Curso", nullable = false, length = 10)
    private String curso;
    
    @Column(name = "Telefono", length = 50)
    private String telefono;
    
    @Column(name = "Id_Inscripcion")
    private Integer idInscripcion;
    
    @Column(name = "Id_Asistencia")
    private Integer idAsistencia;
    
    // Constructores
    public Estudiante() {}
    
    public Estudiante(String nombre, String apellido, Integer documento, Sexo sexo, String direccion, Integer edad, String curso) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.sexo = sexo;
        this.direccion = direccion;
        this.edad = edad;
        this.curso = curso;
    }
    
    // Getters y Setters
    public Integer getIdEstudiante() {
        return idEstudiante;
    }
    
    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    
    public Integer getIdAcudiente() {
        return idAcudiente;
    }
    
    public void setIdAcudiente(Integer idAcudiente) {
        this.idAcudiente = idAcudiente;
    }
    
    public Integer getIdColegio() {
        return idColegio;
    }
    
    public void setIdColegio(Integer idColegio) {
        this.idColegio = idColegio;
    }
    
    public Integer getIdJornada() {
        return idJornada;
    }
    
    public void setIdJornada(Integer idJornada) {
        this.idJornada = idJornada;
    }
    
    public Integer getIdRuta() {
        return idRuta;
    }
    
    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
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
    
    public Integer getDocumento() {
        return documento;
    }
    
    public void setDocumento(Integer documento) {
        this.documento = documento;
    }
    
    public Sexo getSexo() {
        return sexo;
    }
    
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
    public String getEps() {
        return eps;
    }
    
    public void setEps(String eps) {
        this.eps = eps;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public Integer getEdad() {
        return edad;
    }
    
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    
    public String getDiscapacidad() {
        return discapacidad;
    }
    
    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }
    
    public String getEtnia() {
        return etnia;
    }
    
    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }
    
    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }
    
    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
    
    public String getCurso() {
        return curso;
    }
    
    public void setCurso(String curso) {
        this.curso = curso;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public Integer getIdInscripcion() {
        return idInscripcion;
    }
    
    public void setIdInscripcion(Integer idInscripcion) {
        this.idInscripcion = idInscripcion;
    }
    
    public Integer getIdAsistencia() {
        return idAsistencia;
    }
    
    public void setIdAsistencia(Integer idAsistencia) {
        this.idAsistencia = idAsistencia;
    }
    
    // Enum para sexo
    public enum Sexo {
        femenino, masculino
    }
}
