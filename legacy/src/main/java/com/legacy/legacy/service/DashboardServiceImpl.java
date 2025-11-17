package com.legacy.legacy.service;

import com.legacy.legacy.dto.*;
import com.legacy.legacy.model.Alertas;
import com.legacy.legacy.model.Lotes;
import com.legacy.legacy.model.VentasDetalle;
import com.legacy.legacy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardServiceImpl implements DashboardService {
    
    @Autowired
    private VentasRepository ventasRepository;
    
    @Autowired
    private ComprasRepository comprasRepository;
    
    @Autowired
    private LotesRepository lotesRepository;
    
    @Autowired
    private AlertasRepository alertasRepository;
    
    @Autowired
    private VentasDetalleRepository ventasDetalleRepository;
    
    @Autowired
    private ProductosRepository productosRepository;
    
    @Override
    public DashboardResumenDTO getResumen() {
        LocalDate hoy = LocalDate.now();
        int year = hoy.getYear();
        int month = hoy.getMonthValue();
        
        // Ventas
        BigDecimal ventasDelDia = ventasRepository.sumTotalByFecha(hoy);
        BigDecimal ventasDelMes = ventasRepository.sumTotalByMonth(year, month);
        BigDecimal ventasDelAnio = ventasRepository.sumTotalByYear(year);
        
        // Compras
        BigDecimal comprasDelDia = comprasRepository.sumTotalByFecha(hoy);
        BigDecimal comprasDelMes = comprasRepository.sumTotalByMonth(year, month);
        
        // Ganancia neta
        BigDecimal gananciaNetaMes = ventasDelMes.subtract(comprasDelMes);
        
        // Productos con stock bajo
        Long productosStockBajo = lotesRepository.countByStockBajo();
        
        // Alertas activas
        Long alertasActivas = alertasRepository.countAlertasActivas();
        
        // Variaciones (simplificado - comparar con día anterior)
        LocalDate ayer = hoy.minusDays(1);
        BigDecimal ventasAyer = ventasRepository.sumTotalByFecha(ayer);
        Double variacionVentasDia = calcularVariacion(ventasAyer, ventasDelDia);
        
        // Mes anterior
        int mesAnterior = month - 1;
        int yearAnterior = year;
        if (mesAnterior == 0) {
            mesAnterior = 12;
            yearAnterior = year - 1;
        }
        BigDecimal ventasMesAnterior = ventasRepository.sumTotalByMonth(yearAnterior, mesAnterior);
        Double variacionVentasMes = calcularVariacion(ventasMesAnterior, ventasDelMes);
        
        BigDecimal comprasMesAnterior = comprasRepository.sumTotalByMonth(yearAnterior, mesAnterior);
        BigDecimal gananciaMesAnterior = ventasMesAnterior.subtract(comprasMesAnterior);
        Double variacionGanancia = calcularVariacion(gananciaMesAnterior, gananciaNetaMes);
        
        return new DashboardResumenDTO(
            ventasDelDia,
            ventasDelMes,
            ventasDelAnio,
            comprasDelDia,
            comprasDelMes,
            gananciaNetaMes,
            productosStockBajo,
            alertasActivas,
            variacionVentasDia,
            variacionVentasMes,
            variacionGanancia
        );
    }
    
    @Override
    public List<TopProductoDTO> getTopProductos(String periodo) {
        LocalDateTime fechaInicio;
        LocalDateTime fechaFin = LocalDateTime.now();
        
        if ("anio".equals(periodo)) {
            fechaInicio = LocalDateTime.of(LocalDate.now().getYear(), 1, 1, 0, 0);
        } else {
            // Mes actual
            LocalDate hoy = LocalDate.now();
            fechaInicio = LocalDateTime.of(hoy.getYear(), hoy.getMonth(), 1, 0, 0);
        }
        
        List<Object[]> resultados = ventasDetalleRepository.findTopProductosVendidos(fechaInicio, fechaFin);
        
        List<TopProductoDTO> topProductos = new ArrayList<>();
        int limite = 0;
        
        for (Object[] resultado : resultados) {
            if (limite >= 5) break;
            
            Integer productoId = (Integer) resultado[0];
            String productoNombre = (String) resultado[1];
            Long unidadesVendidas = ((Number) resultado[2]).longValue();
            BigDecimal ingresosTotales = (BigDecimal) resultado[3];
            
            // Obtener stock actual del producto
            Integer stockActual = calcularStockProducto(productoId);
            String nivelStock = determinarNivelStock(stockActual);
            
            topProductos.add(new TopProductoDTO(
                productoId,
                productoNombre,
                unidadesVendidas,
                ingresosTotales,
                stockActual,
                nivelStock
            ));
            
            limite++;
        }
        
        return topProductos;
    }
    
    @Override
    public List<VentaMensualDTO> getVentasMensuales(int meses) {
        LocalDateTime fechaInicio = LocalDateTime.now().minusMonths(meses);
        List<Object[]> resultados = ventasRepository.findVentasMensuales(fechaInicio);
        
        List<VentaMensualDTO> ventasMensuales = new ArrayList<>();
        
        for (Object[] resultado : resultados) {
            Integer year = (Integer) resultado[0];
            Integer month = (Integer) resultado[1];
            BigDecimal total = (BigDecimal) resultado[2];
            
            String mes = String.format("%04d-%02d", year, month);
            String mesNombre = LocalDate.of(year, month, 1)
                .getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            
            ventasMensuales.add(new VentaMensualDTO(mes, mesNombre, total));
        }
        
        return ventasMensuales;
    }
    
    @Override
    public List<AlertaRecienteDTO> getAlertasRecientes(int limite) {
        List<Alertas> alertas = alertasRepository.findAlertasRecientes();
        
        List<AlertaRecienteDTO> alertasDTO = new ArrayList<>();
        int contador = 0;
        
        for (Alertas alerta : alertas) {
            if (contador >= limite) {
                break;
            }
            alertasDTO.add(convertirAlerta(alerta));
            contador++;
        }
        
        return alertasDTO;
    }
    
    private AlertaRecienteDTO convertirAlerta(Alertas alerta) {
        Lotes lote = alerta.getLote();
        LocalDate fechaVencimiento = lote.getFechaVencimiento();
        
        Integer diasRestantes = null;
        if (fechaVencimiento != null) {
            diasRestantes = (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
        }
        
        AlertaRecienteDTO.LoteInfoDTO loteInfo = new AlertaRecienteDTO.LoteInfoDTO(
            lote.getId(),
            lote.getNumeroLote(),
            lote.getProducto().getNombre(),
            fechaVencimiento != null ? fechaVencimiento.toString() : null,
            diasRestantes
        );
        
        return new AlertaRecienteDTO(
            alerta.getId(),
            alerta.getTipo().name(),
            alerta.getMensaje(),
            alerta.getFecha(),
            loteInfo
        );
    }
    
    private Integer calcularStockProducto(Integer productoId) {
        List<Lotes> lotes = lotesRepository.findByProductoId(productoId);
        Integer stockTotal = 0;
        
        for (Lotes lote : lotes) {
            stockTotal += lote.getStockActual();
        }
        
        return stockTotal;
    }
    
    private String determinarNivelStock(Integer stock) {
        if (stock < 10) return "BAJO";
        if (stock < 50) return "MEDIO";
        return "ALTO";
    }
    
    private Double calcularVariacion(BigDecimal valorAnterior, BigDecimal valorActual) {
        if (valorAnterior == null || valorAnterior.compareTo(BigDecimal.ZERO) == 0) {
            return valorActual.compareTo(BigDecimal.ZERO) > 0 ? 100.0 : 0.0;
        }
        
        BigDecimal diferencia = valorActual.subtract(valorAnterior);
        BigDecimal porcentaje = diferencia
            .divide(valorAnterior, 4, RoundingMode.HALF_UP)
            .multiply(new BigDecimal("100"));
        
        return porcentaje.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

