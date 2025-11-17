package com.legacy.legacy.service;

import com.legacy.legacy.model.Lotes;
import com.legacy.legacy.repository.LotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LotesServiceImpl implements LotesService {
    
    @Autowired
    private LotesRepository lotesRepository;
    
    @Override
    public List<Lotes> findAll() {
        return lotesRepository.findAll();
    }
    
    @Override
    public Optional<Lotes> findById(Integer id) {
        return lotesRepository.findById(id);
    }
    
    @Override
    public Lotes save(Lotes lotes) {
        return lotesRepository.save(lotes);
    }
    
    @Override
    public Optional<Lotes> update(Integer id, Lotes lotes) {
        if (!lotesRepository.existsById(id)) {
            return Optional.empty();
        }
        lotes.setId(id);
        return Optional.of(lotesRepository.save(lotes));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!lotesRepository.existsById(id)) {
            return false;
        }
        lotesRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return lotesRepository.existsById(id);
    }
}

