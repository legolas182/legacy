package com.legacy.legacy.service;

import com.legacy.legacy.model.Productos;
import com.legacy.legacy.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return productosRepository.save(productos);
    }
    
    @Override
    public Optional<Productos> update(Integer id, Productos productos) {
        if (!productosRepository.existsById(id)) {
            return Optional.empty();
        }
        productos.setId(id);
        return Optional.of(productosRepository.save(productos));
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

