package com.legacy.legacy.service;

import com.legacy.legacy.model.*;
import com.legacy.legacy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportesServiceImpl implements ReportesService {
    
    @Autowired
    private VentasRepository ventasRepository;
    
    @Autowired
    private VentasDetalleRepository ventasDetalleRepository;
    
    @Autowired
    private ComprasRepository comprasRepository;
    
    @Autowired
    private ComprasDetalleRepository comprasDetalleRepository;
    
    @Autowired
    private ProductosRepository productosRepository;
    
    @Autowired
    private LotesRepository lotesRepository;
    
    @Autowired
    private InventarioMovimientosRepository inventarioMovimientosRepository;
    
    @Override
    public Map<String, Object> getReporteVentas(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);
        
        List<Ventas> ventas = ventasRepository.findByFechaBetween(inicio, fin);
        
        BigDecimal totalGeneral = BigDecimal.ZERO;
        for (Ventas venta : ventas) {
            totalGeneral = totalGeneral.add(venta.getTotalGeneral());
        }
        
        List<Map<String, Object>> ventasDetalle = new ArrayList<>();
        
        for (Ventas v : ventas) {
            Map<String, Object> ventaMap = new HashMap<>();
            ventaMap.put("id", v.getId());
            ventaMap.put("fecha", v.getFecha());
            
            Map<String, Object> clienteMap = new HashMap<>();
            clienteMap.put("id", v.getCliente().getId());
            clienteMap.put("nombre", v.getCliente().getNombre());
            ventaMap.put("cliente", clienteMap);
            
            ventaMap.put("metodoPago", v.getMetodoPago() != null ? v.getMetodoPago().getNombre() : null);
            ventaMap.put("totalGeneral", v.getTotalGeneral());
            ventaMap.put("requiereFormula", v.getRequiereFormula());
            ventaMap.put("numeroFormula", v.getNumeroFormula());
            
            List<VentasDetalle> detalles = ventasDetalleRepository.findByVentaId(v.getId());
            List<Map<String, Object>> detalleList = new ArrayList<>();
            
            for (VentasDetalle d : detalles) {
                Map<String, Object> detalleMap = new HashMap<>();
                detalleMap.put("producto", d.getProducto().getNombre());
                detalleMap.put("cantidad", d.getCantidad());
                detalleMap.put("precioUnitario", d.getPrecioUnitario());
                BigDecimal subtotal = d.getPrecioUnitario().multiply(new BigDecimal(d.getCantidad()));
                detalleMap.put("subtotal", subtotal);
                detalleList.add(detalleMap);
            }
            
            ventaMap.put("detalle", detalleList);
            ventasDetalle.add(ventaMap);
        }
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("fechaInicio", fechaInicio.toString());
        reporte.put("fechaFin", fechaFin.toString());
        reporte.put("totalGeneral", totalGeneral);
        reporte.put("cantidadVentas", ventas.size());
        reporte.put("ventas", ventasDetalle);
        
        return reporte;
    }
    
    @Override
    public Map<String, Object> getReporteCompras(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);
        
        List<Compras> compras = comprasRepository.findByFechaBetween(inicio, fin);
        
        BigDecimal totalGeneral = BigDecimal.ZERO;
        for (Compras compra : compras) {
            totalGeneral = totalGeneral.add(compra.getTotal());
        }
        
        List<Map<String, Object>> comprasDetalle = new ArrayList<>();
        
        for (Compras c : compras) {
            Map<String, Object> compraMap = new HashMap<>();
            compraMap.put("id", c.getId());
            compraMap.put("fecha", c.getFecha());
            
            Map<String, Object> proveedorMap = new HashMap<>();
            proveedorMap.put("id", c.getProveedor().getId());
            proveedorMap.put("nombre", c.getProveedor().getNombre());
            compraMap.put("proveedor", proveedorMap);
            
            compraMap.put("numeroFactura", c.getNumeroFactura());
            compraMap.put("total", c.getTotal());
            
            List<ComprasDetalle> detalles = comprasDetalleRepository.findByCompraId(c.getId());
            List<Map<String, Object>> detalleList = new ArrayList<>();
            
            for (ComprasDetalle d : detalles) {
                Map<String, Object> detalleMap = new HashMap<>();
                detalleMap.put("producto", d.getProducto().getNombre());
                detalleMap.put("cantidad", d.getCantidad());
                detalleMap.put("precioUnitario", d.getPrecioUnitario());
                BigDecimal subtotal = d.getPrecioUnitario().multiply(new BigDecimal(d.getCantidad()));
                detalleMap.put("subtotal", subtotal);
                detalleMap.put("numeroLote", d.getNumeroLote() != null ? d.getNumeroLote() : "");
                detalleMap.put("fechaVencimiento", d.getFechaVencimiento() != null ? d.getFechaVencimiento().toString() : "");
                detalleList.add(detalleMap);
            }
            
            compraMap.put("detalle", detalleList);
            comprasDetalle.add(compraMap);
        }
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("fechaInicio", fechaInicio.toString());
        reporte.put("fechaFin", fechaFin.toString());
        reporte.put("totalGeneral", totalGeneral);
        reporte.put("cantidadCompras", compras.size());
        reporte.put("compras", comprasDetalle);
        
        return reporte;
    }
    
    @Override
    public Map<String, Object> getReporteInventario(boolean incluirStockBajo, boolean incluirVencimientos) {
        List<Productos> productos = productosRepository.findAll();
        
        BigDecimal valorTotal = BigDecimal.ZERO;
        List<Map<String, Object>> productosList = new ArrayList<>();
        List<Map<String, Object>> lotesProximosVencer = new ArrayList<>();
        
        for (Productos producto : productos) {
            List<Lotes> lotes = lotesRepository.findByProductoId(producto.getId());
            
            Integer stockTotal = 0;
            for (Lotes lote : lotes) {
                stockTotal += lote.getStockActual();
            }
            
            boolean stockBajo = stockTotal < 10;
            
            if (!incluirStockBajo || stockBajo) {
                List<Map<String, Object>> lotesList = new ArrayList<>();
                
                for (Lotes l : lotes) {
                    BigDecimal valorLote = BigDecimal.ZERO;
                    if (l.getStockActual() > 0) {
                        // Aquí podrías obtener el precio de compra del lote
                        valorLote = new BigDecimal(l.getStockActual()).multiply(new BigDecimal("10")); // Placeholder
                    }
                    valorTotal = valorTotal.add(valorLote);
                    
                    Map<String, Object> loteMap = new HashMap<>();
                    loteMap.put("numeroLote", l.getNumeroLote());
                    loteMap.put("stockActual", l.getStockActual());
                    loteMap.put("fechaVencimiento", l.getFechaVencimiento() != null ? l.getFechaVencimiento().toString() : "");
                    lotesList.add(loteMap);
                }
                
                Map<String, Object> productoMap = new HashMap<>();
                productoMap.put("id", producto.getId());
                productoMap.put("nombre", producto.getNombre());
                productoMap.put("categoria", producto.getCategoria() != null ? producto.getCategoria().getNombre() : "");
                productoMap.put("stockTotal", stockTotal);
                productoMap.put("stockBajo", stockBajo);
                productoMap.put("lotes", lotesList);
                productosList.add(productoMap);
            }
            
            // Lotes próximos a vencer
            if (incluirVencimientos) {
                LocalDate fechaInicio = LocalDate.now();
                LocalDate fechaFin = fechaInicio.plusDays(30);
                List<Lotes> proximos = lotesRepository.findProximosVencer(fechaInicio, fechaFin);
                
                for (Lotes lote : proximos) {
                    if (lote.getProducto().getId().equals(producto.getId())) {
                        long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), lote.getFechaVencimiento());
                        
                        Map<String, Object> loteProximoMap = new HashMap<>();
                        loteProximoMap.put("numeroLote", lote.getNumeroLote());
                        loteProximoMap.put("producto", producto.getNombre());
                        loteProximoMap.put("fechaVencimiento", lote.getFechaVencimiento().toString());
                        loteProximoMap.put("diasRestantes", (int) diasRestantes);
                        loteProximoMap.put("stockActual", lote.getStockActual());
                        lotesProximosVencer.add(loteProximoMap);
                    }
                }
            }
        }
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("fechaGeneracion", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        reporte.put("valorTotalInventario", valorTotal);
        reporte.put("productos", productosList);
        reporte.put("lotesProximosVencer", lotesProximosVencer);
        
        return reporte;
    }
    
    @Override
    public Map<String, Object> getReporteProductos() {
        List<Productos> productos = productosRepository.findAll();
        
        List<Map<String, Object>> productosList = new ArrayList<>();
        
        for (Productos p : productos) {
            List<Lotes> lotes = lotesRepository.findByProductoId(p.getId());
            
            Integer stockActual = 0;
            for (Lotes lote : lotes) {
                stockActual += lote.getStockActual();
            }
            
            Map<String, Object> productoMap = new HashMap<>();
            productoMap.put("id", p.getId());
            productoMap.put("nombre", p.getNombre());
            productoMap.put("concentracion", p.getConcentracion() != null ? p.getConcentracion() : "");
            productoMap.put("formaFarmaceutica", p.getFormaFarmaceutica() != null ? p.getFormaFarmaceutica() : "");
            productoMap.put("categoria", p.getCategoria() != null ? p.getCategoria().getNombre() : "");
            productoMap.put("laboratorio", p.getLaboratorio() != null ? p.getLaboratorio().getNombre() : "");
            productoMap.put("stockActual", stockActual);
            productoMap.put("requiereFormula", p.getRequiereFormula() != null ? p.getRequiereFormula() : false);
            
            productosList.add(productoMap);
        }
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("fechaGeneracion", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        reporte.put("totalProductos", productos.size());
        reporte.put("productos", productosList);
        
        return reporte;
    }
    
    @Override
    public Map<String, Object> getReporteVencimientos(int dias, boolean incluirVencidos) {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(dias);
        
        List<Lotes> lotesProximos = lotesRepository.findProximosVencer(fechaInicio, fechaFin);
        List<Lotes> lotesVencidos = new ArrayList<>();
        
        if (incluirVencidos) {
            lotesVencidos = lotesRepository.findVencidos(fechaInicio);
        }
        
        List<Map<String, Object>> lotesList = new ArrayList<>();
        
        for (Lotes lote : lotesProximos) {
            long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), lote.getFechaVencimiento());
            
            Map<String, Object> loteMap = new HashMap<>();
            loteMap.put("numeroLote", lote.getNumeroLote());
            loteMap.put("producto", lote.getProducto().getNombre());
            loteMap.put("fechaVencimiento", lote.getFechaVencimiento().toString());
            loteMap.put("diasRestantes", (int) diasRestantes);
            loteMap.put("stockActual", lote.getStockActual());
            loteMap.put("estado", diasRestantes >= 0 ? "PROXIMO_VENCER" : "VENCIDO");
            lotesList.add(loteMap);
        }
        
        for (Lotes lote : lotesVencidos) {
            long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), lote.getFechaVencimiento());
            
            Map<String, Object> loteMap = new HashMap<>();
            loteMap.put("numeroLote", lote.getNumeroLote());
            loteMap.put("producto", lote.getProducto().getNombre());
            loteMap.put("fechaVencimiento", lote.getFechaVencimiento().toString());
            loteMap.put("diasRestantes", (int) diasRestantes);
            loteMap.put("stockActual", lote.getStockActual());
            loteMap.put("estado", "VENCIDO");
            lotesList.add(loteMap);
        }
        
        // Ordenar por fecha de vencimiento
        Collections.sort(lotesList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> a, Map<String, Object> b) {
                String fechaA = (String) a.get("fechaVencimiento");
                String fechaB = (String) b.get("fechaVencimiento");
                return fechaA.compareTo(fechaB);
            }
        });
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("dias", dias);
        reporte.put("fechaGeneracion", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        reporte.put("cantidadLotes", lotesList.size());
        reporte.put("lotes", lotesList);
        
        return reporte;
    }
    
    @Override
    public Map<String, Object> getReporteMovimientos(LocalDate fechaInicio, LocalDate fechaFin, String tipo) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);
        
        List<InventarioMovimientos> movimientos;
        
        if (tipo != null && !tipo.isEmpty()) {
            try {
                com.legacy.legacy.model.enums.TipoMovimiento tipoEnum = 
                    com.legacy.legacy.model.enums.TipoMovimiento.valueOf(tipo);
                movimientos = inventarioMovimientosRepository.findByFechaBetweenAndTipo(inicio, fin, tipoEnum);
            } catch (IllegalArgumentException e) {
                movimientos = inventarioMovimientosRepository.findByFechaBetween(inicio, fin);
            }
        } else {
            movimientos = inventarioMovimientosRepository.findByFechaBetween(inicio, fin);
        }
        
        Integer totalEntradas = inventarioMovimientosRepository.sumEntradasByFechaBetween(inicio, fin);
        Integer totalSalidas = inventarioMovimientosRepository.sumSalidasByFechaBetween(inicio, fin);
        
        List<Map<String, Object>> movimientosList = new ArrayList<>();
        
        for (InventarioMovimientos m : movimientos) {
            Map<String, Object> movimientoMap = new HashMap<>();
            movimientoMap.put("id", m.getId());
            movimientoMap.put("fecha", m.getFecha());
            movimientoMap.put("tipo", m.getTipo().name());
            movimientoMap.put("origen", m.getOrigen().name());
            movimientoMap.put("producto", m.getLote().getProducto().getNombre());
            movimientoMap.put("numeroLote", m.getLote().getNumeroLote());
            movimientoMap.put("cantidad", m.getCantidad());
            movimientoMap.put("descripcion", m.getDescripcion() != null ? m.getDescripcion() : "");
            movimientosList.add(movimientoMap);
        }
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("fechaInicio", fechaInicio.toString());
        reporte.put("fechaFin", fechaFin.toString());
        reporte.put("totalEntradas", totalEntradas);
        reporte.put("totalSalidas", totalSalidas);
        reporte.put("movimientos", movimientosList);
        
        return reporte;
    }
}

