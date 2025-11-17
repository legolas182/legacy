package com.legacy.legacy.service;

import com.legacy.legacy.model.Categorias;
import com.legacy.legacy.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriasServiceImpl implements CategoriasService {
    
    @Autowired
    private CategoriasRepository categoriasRepository;
    
    @Override
    public List<Categorias> findAll() {
        return categoriasRepository.findAll();
    }
    
    @Override
    public Optional<Categorias> findById(Integer id) {
        return categoriasRepository.findById(id);
    }
    
    @Override
    public Categorias save(Categorias categorias) {
        return categoriasRepository.save(categorias);
    }
    
    @Override
    public Optional<Categorias> update(Integer id, Categorias categorias) {
        if (!categoriasRepository.existsById(id)) {
            return Optional.empty();
        }
        categorias.setId(id);
        return Optional.of(categoriasRepository.save(categorias));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!categoriasRepository.existsById(id)) {
            return false;
        }
        categoriasRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return categoriasRepository.existsById(id);
    }
}

