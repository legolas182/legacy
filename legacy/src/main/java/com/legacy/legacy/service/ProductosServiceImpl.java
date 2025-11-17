package com.legacy.legacy.service;

import com.legacy.legacy.model.Productos;
import com.legacy.legacy.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosServiceImpl implements ProductosService {
    
    @Autowired
    private ProductosRepository productosRepository;
    
    @Override
    public List<Productos> findAll() {
        return productosRepository.findAll();
    }
    
    @Override
    public Optional<Productos> findById(Integer id) {
        return productosRepository.findById(id);
    }
    
    @Override
    public Productos save(Productos productos) {
        calcularUtilidadEIVA(productos);
        return productosRepository.save(productos);
    }
    
    @Override
    public Optional<Productos> update(Integer id, Productos productos) {
        if (!productosRepository.existsById(id)) {
            return Optional.empty();
        }
        productos.setId(id);
        calcularUtilidadEIVA(productos);
        return Optional.of(productosRepository.save(productos));
    }
    
    private void calcularUtilidadEIVA(Productos productos) {
        // Calcular utilidad: valorVenta - valorCompra
        if (productos.getValorVenta() != null && productos.getValorCompra() != null) {
            BigDecimal utilidad = productos.getValorVenta().subtract(productos.getValorCompra());
            productos.setUtilidad(utilidad.setScale(2, RoundingMode.HALF_UP));
        } else {
            productos.setUtilidad(null);
        }
        
        // Calcular IVA: valorVenta * 0.19 (19% IVA en Colombia)
        if (productos.getValorVenta() != null) {
            BigDecimal porcentajeIVA = new BigDecimal("0.19");
            BigDecimal iva = productos.getValorVenta().multiply(porcentajeIVA);
            productos.setIva(iva.setScale(2, RoundingMode.HALF_UP));
        } else {
            productos.setIva(null);
        }
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!productosRepository.existsById(id)) {
            return false;
        }
        productosRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return productosRepository.existsById(id);
    }
}

