package com.legacy.legacy.controller;

import com.legacy.legacy.dto.*;
import com.legacy.legacy.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping("/resumen")
    public ResponseEntity<DashboardResumenDTO> getResumen() {
        return ResponseEntity.ok(dashboardService.getResumen());
    }
    
    @GetMapping("/top-productos")
    public ResponseEntity<List<TopProductoDTO>> getTopProductos(
            @RequestParam(defaultValue = "mes") String periodo) {
        return ResponseEntity.ok(dashboardService.getTopProductos(periodo));
    }
    
    @GetMapping("/ventas-mensuales")
    public ResponseEntity<List<VentaMensualDTO>> getVentasMensuales(
            @RequestParam(defaultValue = "6") int meses) {
        return ResponseEntity.ok(dashboardService.getVentasMensuales(meses));
    }
    
    @GetMapping("/alertas-recientes")
    public ResponseEntity<List<AlertaRecienteDTO>> getAlertasRecientes(
            @RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(dashboardService.getAlertasRecientes(limite));
    }
}

