package com.ciempies.sgi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ciempies.sgi.repository.UsuarioRepository;
import com.ciempies.sgi.repository.EstudianteRepository;
import com.ciempies.sgi.repository.RutaRepository;
import com.ciempies.sgi.repository.AsistenciaRepository;
import com.ciempies.sgi.repository.InscripcionRepository;
import com.ciempies.sgi.repository.ZonaRepository;

@Controller
@RequestMapping("/")
public class ViewController {
    
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.version}")
    private String appVersion;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private RutaRepository rutaRepository;
    
    @Autowired
    private AsistenciaRepository asistenciaRepository;
    
    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    @Autowired
    private ZonaRepository zonaRepository;
    
    @GetMapping
    public String index(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Inicio - " + appName);
        return "index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Dashboard - " + appName);
        
        // Estadísticas básicas
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("totalEstudiantes", estudianteRepository.count());
        model.addAttribute("totalRutas", rutaRepository.count());
        model.addAttribute("totalAsistencias", asistenciaRepository.count());
        model.addAttribute("totalInscripciones", inscripcionRepository.count());
        model.addAttribute("totalZonas", zonaRepository.count());
        
        return "dashboard";
    }
    
    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Gestión de Usuarios - " + appName);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios";
    }
    
    @GetMapping("/estudiantes")
    public String estudiantes(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Gestión de Estudiantes - " + appName);
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "estudiantes";
    }
    
    @GetMapping("/rutas")
    public String rutas(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Gestión de Rutas - " + appName);
        model.addAttribute("rutas", rutaRepository.findAll());
        return "rutas";
    }
    
    @GetMapping("/asistencias")
    public String asistencias(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Gestión de Asistencias - " + appName);
        model.addAttribute("asistencias", asistenciaRepository.findAll());
        return "asistencias";
    }
    
    @GetMapping("/inscripciones")
    public String inscripciones(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Gestión de Inscripciones - " + appName);
        model.addAttribute("inscripciones", inscripcionRepository.findAll());
        return "inscripciones";
    }
    
    @GetMapping("/zonas")
    public String zonas(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Gestión de Zonas - " + appName);
        model.addAttribute("zonas", zonaRepository.findAll());
        return "zonas";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Iniciar Sesión - " + appName);
        return "login";
    }
    
    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("title", "Registro - " + appName);
        return "registro";
    }
}


