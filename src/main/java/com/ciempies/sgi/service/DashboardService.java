package com.ciempies.sgi.service;

import com.ciempies.sgi.repository.AsistenciaRepository;
import com.ciempies.sgi.repository.EstudianteRepository;
import com.ciempies.sgi.repository.RutaRepository;
import com.ciempies.sgi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Estadísticas básicas
        stats.put("totalEstudiantes", estudianteRepository.countByActivoTrue());
        stats.put("rutasActivas", rutaRepository.countByActivaTrue());
        stats.put("totalUsuarios", usuarioRepository.countByActivoTrue());
        
        // Asistencias de hoy
        LocalDate hoy = LocalDate.now();
        long asistenciasHoy = asistenciaRepository.countByFecha(hoy);
        stats.put("asistenciasHoy", asistenciasHoy);
        
        // Estadísticas por curso
        Map<String, Long> estudiantesPorCurso = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            long count = estudianteRepository.countByCursoAndActivoTrue(i);
            estudiantesPorCurso.put("Curso " + i, count);
        }
        stats.put("estudiantesPorCurso", estudiantesPorCurso);
        
        // Estadísticas por sexo
        Map<String, Long> estudiantesPorSexo = new HashMap<>();
        estudiantesPorSexo.put("Masculino", estudianteRepository.countBySexoAndActivoTrue("MASCULINO"));
        estudiantesPorSexo.put("Femenino", estudianteRepository.countBySexoAndActivoTrue("FEMENINO"));
        stats.put("estudiantesPorSexo", estudiantesPorSexo);
        
        // Rutas con más estudiantes
        List<Object[]> rutasConEstudiantes = rutaRepository.findRutasWithStudentCount();
        stats.put("rutasConEstudiantes", rutasConEstudiantes);
        
        return stats;
    }

    public Map<String, Object> getChartData() {
        Map<String, Object> chartData = new HashMap<>();
        
        // Datos para gráfico de asistencias por ruta
        List<Object[]> asistenciasPorRuta = asistenciaRepository.findAsistenciasByRutaAndDateRange(
            LocalDate.now().minusDays(7), LocalDate.now());
        chartData.put("asistenciasPorRuta", asistenciasPorRuta);
        
        // Datos para gráfico de estudiantes por curso
        Map<String, Long> estudiantesPorCurso = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            long count = estudianteRepository.countByCursoAndActivoTrue(i);
            estudiantesPorCurso.put("Curso " + i, count);
        }
        chartData.put("estudiantesPorCurso", estudiantesPorCurso);
        
        // Datos para gráfico de asistencias por estado
        Map<String, Long> asistenciasPorEstado = new HashMap<>();
        asistenciasPorEstado.put("Presente", asistenciaRepository.countByEstadoAndFecha("PRESENTE", LocalDate.now()));
        asistenciasPorEstado.put("Ausente", asistenciaRepository.countByEstadoAndFecha("AUSENTE", LocalDate.now()));
        asistenciasPorEstado.put("Tardanza", asistenciaRepository.countByEstadoAndFecha("TARDANZA", LocalDate.now()));
        chartData.put("asistenciasPorEstado", asistenciasPorEstado);
        
        return chartData;
    }
}
