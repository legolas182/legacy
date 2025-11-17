package com.legacy.legacy.service;

import com.legacy.legacy.model.Productos;
import com.legacy.legacy.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosService {
    
    @Autowired
    private ProductosRepository productosRepository;
    
    public List<Productos> findAll() {
        return productosRepository.findAll();
    }
    
    public Optional<Productos> findById(Integer id) {
        return productosRepository.findById(id);
    }
    
    public Productos save(Productos productos) {
        return productosRepository.save(productos);
    }
    
    public void deleteById(Integer id) {
        productosRepository.deleteById(id);
    }
}

