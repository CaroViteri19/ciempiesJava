package com.ciempies.sgi.controller;

import com.ciempies.sgi.dto.ApiResponse;
import com.ciempies.sgi.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Endpoints para el dashboard del sistema")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas del dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'MONITOR')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        try {
            Map<String, Object> stats = dashboardService.getDashboardStats();
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas exitosamente", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }

    @GetMapping("/chart-data")
    @Operation(summary = "Obtener datos para gráficos")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'MONITOR')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getChartData() {
        try {
            Map<String, Object> chartData = dashboardService.getChartData();
            return ResponseEntity.ok(ApiResponse.success("Datos de gráficos obtenidos exitosamente", chartData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Error al obtener datos de gráficos: " + e.getMessage()));
        }
    }
}
