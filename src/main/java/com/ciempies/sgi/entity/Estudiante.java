package com.ciempies.sgi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "estudiante")
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Estudiante")
    private Long idEstudiante;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Column(name = "Nombre", nullable = false)
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder 50 caracteres")
    @Column(name = "Apellido", nullable = false)
    private String apellido;
    
    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 20, message = "El documento no puede exceder 20 caracteres")
    @Column(name = "Documento", unique = true, nullable = false)
    private String documento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Sexo", nullable = false)
    private Sexo sexo;
    
    @NotBlank(message = "La EPS es obligatoria")
    @Size(max = 100, message = "La EPS no puede exceder 100 caracteres")
    @Column(name = "EPS", nullable = false)
    private String eps;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    @Column(name = "Direccion", nullable = false)
    private String direccion;
    
    @Min(value = 3, message = "La edad mínima es 3 años")
    @Max(value = 18, message = "La edad máxima es 18 años")
    @Column(name = "Edad", nullable = false)
    private Integer edad;
    
    @Size(max = 100, message = "La discapacidad no puede exceder 100 caracteres")
    @Column(name = "Discapacidad")
    private String discapacidad;
    
    @Size(max = 50, message = "La etnia no puede exceder 50 caracteres")
    @Column(name = "Etnia")
    private String etnia;
    
    @NotBlank(message = "El curso es obligatorio")
    @Size(max = 20, message = "El curso no puede exceder 20 caracteres")
    @Column(name = "Curso", nullable = false)
    private String curso;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos")
    @Column(name = "Telefono", nullable = false)
    private String telefono;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Ruta")
    private Ruta ruta;
    
    @Column(name = "Fecha_Registro")
    private LocalDate fechaRegistro;
    
    @Column(name = "Activo")
    private Boolean activo = true;
    
    // Constructores
    public Estudiante() {
        this.fechaRegistro = LocalDate.now();
    }
    
    public Estudiante(String nombre, String apellido, String documento, Sexo sexo, 
                     String eps, String direccion, Integer edad, String curso, String telefono) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.sexo = sexo;
        this.eps = eps;
        this.direccion = direccion;
        this.edad = edad;
        this.curso = curso;
        this.telefono = telefono;
    }
    
    // Getters y Setters
    public Long getIdEstudiante() {
        return idEstudiante;
    }
    
    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
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
    
    public String getDocumento() {
        return documento;
    }
    
    public void setDocumento(String documento) {
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
    
    public Ruta getRuta() {
        return ruta;
    }
    
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
    
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    // Métodos de utilidad
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    // Enum para sexo
    public enum Sexo {
        FEMENINO,
        MASCULINO,
        OTRO
    }
}
