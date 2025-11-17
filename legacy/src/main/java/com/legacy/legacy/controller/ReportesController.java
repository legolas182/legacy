package com.legacy.legacy.controller;

import com.legacy.legacy.service.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReportesController {
    
    @Autowired
    private ReportesService reportesService;
    
    // Reporte de Ventas
    @GetMapping("/ventas")
    public ResponseEntity<Map<String, Object>> getReporteVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reportesService.getReporteVentas(fechaInicio, fechaFin));
    }
    
    // Reporte de Compras
    @GetMapping("/compras")
    public ResponseEntity<Map<String, Object>> getReporteCompras(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reportesService.getReporteCompras(fechaInicio, fechaFin));
    }
    
    // Reporte de Inventario
    @GetMapping("/inventario")
    public ResponseEntity<Map<String, Object>> getReporteInventario(
            @RequestParam(defaultValue = "true") boolean incluirStockBajo,
            @RequestParam(defaultValue = "true") boolean incluirVencimientos) {
        return ResponseEntity.ok(reportesService.getReporteInventario(incluirStockBajo, incluirVencimientos));
    }
    
    // Reporte de Productos
    @GetMapping("/productos")
    public ResponseEntity<Map<String, Object>> getReporteProductos() {
        return ResponseEntity.ok(reportesService.getReporteProductos());
    }
    
    // Reporte de Vencimientos
    @GetMapping("/vencimientos")
    public ResponseEntity<Map<String, Object>> getReporteVencimientos(
            @RequestParam(defaultValue = "30") int dias,
            @RequestParam(defaultValue = "true") boolean incluirVencidos) {
        return ResponseEntity.ok(reportesService.getReporteVencimientos(dias, incluirVencidos));
    }
    
    // Reporte de Movimientos de Inventario
    @GetMapping("/movimientos")
    public ResponseEntity<Map<String, Object>> getReporteMovimientos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) String tipo) {
        return ResponseEntity.ok(reportesService.getReporteMovimientos(fechaInicio, fechaFin, tipo));
    }
}

