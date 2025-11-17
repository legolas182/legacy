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
public class VentasDetalleServiceImpl implements VentasDetalleService {
    
    @Autowired
    private VentasDetalleRepository ventasDetalleRepository;
    
    @Autowired
    private ComprasDetalleRepository comprasDetalleRepository;
    
    @Autowired
    private LotesRepository lotesRepository;
    
    @Override
    public List<VentasDetalle> findAll() {
        return ventasDetalleRepository.findAll();
    }
    
    @Override
    public Optional<VentasDetalle> findById(Integer id) {
        return ventasDetalleRepository.findById(id);
    }
    
    @Override
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
    
    @Override
    @Transactional
    public Optional<VentasDetalle> update(Integer id, VentasDetalle ventasDetalle) {
        if (!ventasDetalleRepository.existsById(id)) {
            return Optional.empty();
        }
        ventasDetalle.setId(id);
        
        // Aplicar la misma lógica de save para calcular precio de compra y ganancia
        BigDecimal precioCompra = null;
        
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
        
        if (precioCompra == null && ventasDetalle.getProducto() != null) {
            Optional<BigDecimal> ultimoPrecio = comprasDetalleRepository
                .findUltimoPrecioCompraByProducto(ventasDetalle.getProducto().getId());
            if (ultimoPrecio.isPresent()) {
                precioCompra = ultimoPrecio.get();
            }
        }
        
        if (precioCompra != null) {
            ventasDetalle.setPrecioCompra(precioCompra);
        }
        
        ventasDetalle.calcularGanancia();
        
        return Optional.of(ventasDetalleRepository.save(ventasDetalle));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!ventasDetalleRepository.existsById(id)) {
            return false;
        }
        ventasDetalleRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return ventasDetalleRepository.existsById(id);
    }
}

