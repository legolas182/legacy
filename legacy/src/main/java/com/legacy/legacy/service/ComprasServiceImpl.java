package com.legacy.legacy.service;

import com.legacy.legacy.model.Compras;
import com.legacy.legacy.repository.ComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComprasServiceImpl implements ComprasService {
    
    @Autowired
    private ComprasRepository comprasRepository;
    
    @Autowired
    private SucursalService sucursalService;
    
    @Override
    public List<Compras> findAll() {
        // Filtrar por sucursal actual
        Integer sucursalId = sucursalService.getSucursalActualId();
        return comprasRepository.findAll().stream()
            .filter(c -> c.getSucursal() != null && c.getSucursal().getId().equals(sucursalId))
            .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public Optional<Compras> findById(Integer id) {
        Optional<Compras> compraOpt = comprasRepository.findById(id);
        if (compraOpt.isPresent()) {
            Compras compra = compraOpt.get();
            Integer sucursalId = sucursalService.getSucursalActualId();
            // Verificar que la compra pertenezca a la sucursal actual
            if (compra.getSucursal() != null && compra.getSucursal().getId().equals(sucursalId)) {
                return Optional.of(compra);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }
    
    @Override
    public Compras save(Compras compras) {
        // Asignar sucursal automáticamente si no está asignada
        if (compras.getSucursal() == null) {
            compras.setSucursal(sucursalService.getSucursalActual());
        }
        return comprasRepository.save(compras);
    }
    
    @Override
    public Optional<Compras> update(Integer id, Compras compras) {
        if (!comprasRepository.existsById(id)) {
            return Optional.empty();
        }
        compras.setId(id);
        return Optional.of(comprasRepository.save(compras));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!comprasRepository.existsById(id)) {
            return false;
        }
        comprasRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return comprasRepository.existsById(id);
    }
}

