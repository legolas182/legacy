package com.legacy.legacy.service;

import com.legacy.legacy.dto.*;
import java.util.List;

public interface DashboardService {
    DashboardResumenDTO getResumen();
    List<TopProductoDTO> getTopProductos(String periodo);
    List<VentaMensualDTO> getVentasMensuales(int meses);
    List<AlertaRecienteDTO> getAlertasRecientes(int limite);
}
