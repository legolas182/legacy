package com.legacy.legacy.service;

import java.time.LocalDate;
import java.util.Map;

public interface ReportesService {
    Map<String, Object> getReporteVentas(LocalDate fechaInicio, LocalDate fechaFin);
    Map<String, Object> getReporteCompras(LocalDate fechaInicio, LocalDate fechaFin);
    Map<String, Object> getReporteInventario(boolean incluirStockBajo, boolean incluirVencimientos);
    Map<String, Object> getReporteProductos();
    Map<String, Object> getReporteVencimientos(int dias, boolean incluirVencidos);
    Map<String, Object> getReporteMovimientos(LocalDate fechaInicio, LocalDate fechaFin, String tipo);
}
