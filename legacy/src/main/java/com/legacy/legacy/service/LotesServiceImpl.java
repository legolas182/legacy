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
    
    @Autowired
    private SucursalService sucursalService;
    
    @Override
    public List<Lotes> findAll() {
        // Filtrar por sucursal actual
        Integer sucursalId = sucursalService.getSucursalActualId();
        return lotesRepository.findAll().stream()
            .filter(l -> l.getSucursal() != null && l.getSucursal().getId().equals(sucursalId))
            .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public Optional<Lotes> findById(Integer id) {
        Optional<Lotes> loteOpt = lotesRepository.findById(id);
        if (loteOpt.isPresent()) {
            Lotes lote = loteOpt.get();
            Integer sucursalId = sucursalService.getSucursalActualId();
            // Verificar que el lote pertenezca a la sucursal actual
            if (lote.getSucursal() != null && lote.getSucursal().getId().equals(sucursalId)) {
                return Optional.of(lote);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }
    
    @Override
    public Lotes save(Lotes lotes) {
        // Asignar sucursal automáticamente si no está asignada
        if (lotes.getSucursal() == null) {
            lotes.setSucursal(sucursalService.getSucursalActual());
        }
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

