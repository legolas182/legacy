package com.legacy.legacy.service;

import com.legacy.legacy.model.Contactos;
import com.legacy.legacy.repository.ContactosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContactosServiceImpl implements ContactosService {
    
    @Autowired
    private ContactosRepository contactosRepository;
    
    @Override
    public List<Contactos> findAll() {
        return contactosRepository.findAll();
    }
    
    @Override
    public Optional<Contactos> findById(Integer id) {
        return contactosRepository.findById(id);
    }
    
    @Override
    public Contactos save(Contactos contactos) {
        return contactosRepository.save(contactos);
    }
    
    @Override
    public Optional<Contactos> update(Integer id, Contactos contactos) {
        if (!contactosRepository.existsById(id)) {
            return Optional.empty();
        }
        contactos.setId(id);
        return Optional.of(contactosRepository.save(contactos));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!contactosRepository.existsById(id)) {
            return false;
        }
        contactosRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return contactosRepository.existsById(id);
    }
}

