package com.legacy.legacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResumenDTO {
    private BigDecimal ventasDelDia;
    private BigDecimal ventasDelMes;
    private BigDecimal ventasDelAnio;
    private BigDecimal comprasDelDia;
    private BigDecimal comprasDelMes;
    private BigDecimal gananciaNetaMes;
    private Long productosStockBajo;
    private Long alertasActivas;
    private Double variacionVentasDia;
    private Double variacionVentasMes;
    private Double variacionGanancia;
}

