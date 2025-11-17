package com.legacy.legacy.service;

import com.legacy.legacy.model.VentasDetalle;
import com.legacy.legacy.repository.VentasDetalleRepository;
import com.legacy.legacy.repository.ComprasDetalleRepository;
import com.legacy.legacy.repository.LotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VentasDetalleService {
    
    @Autowired
    private VentasDetalleRepository ventasDetalleRepository;
    
    @Autowired
    private ComprasDetalleRepository comprasDetalleRepository;
    
    @Autowired
    private LotesRepository lotesRepository;
    
    public List<VentasDetalle> findAll() {
        return ventasDetalleRepository.findAll();
    }
    
    public Optional<VentasDetalle> findById(Integer id) {
        return ventasDetalleRepository.findById(id);
    }
    
    @Transactional
    public VentasDetalle save(VentasDetalle ventasDetalle) {
        // Buscar el precio de compra
        BigDecimal precioCompra = null;
        
        // Si tiene lote asignado, buscar precio por lote
        if (ventasDetalle.getLote() != null && ventasDetalle.getLote().getNumeroLote() != null) {
            Optional<BigDecimal> precioPorLote = comprasDetalleRepository
                .findPrecioCompraByLoteAndProducto(
                    ventasDetalle.getLote().getNumeroLote(),
                    ventasDetalle.getProducto().getId()
                );
            if (precioPorLote.isPresent()) {
                precioCompra = precioPorLote.get();
            }
        }
        
        // Si no se encontró precio por lote, buscar el último precio de compra del producto
        if (precioCompra == null && ventasDetalle.getProducto() != null) {
            Optional<BigDecimal> ultimoPrecio = comprasDetalleRepository
                .findUltimoPrecioCompraByProducto(ventasDetalle.getProducto().getId());
            if (ultimoPrecio.isPresent()) {
                precioCompra = ultimoPrecio.get();
            }
        }
        
        // Asignar precio de compra
        if (precioCompra != null) {
            ventasDetalle.setPrecioCompra(precioCompra);
        }
        
        // Calcular ganancia y porcentaje
        ventasDetalle.calcularGanancia();
        
        return ventasDetalleRepository.save(ventasDetalle);
    }
    
    public void deleteById(Integer id) {
        ventasDetalleRepository.deleteById(id);
    }
}

