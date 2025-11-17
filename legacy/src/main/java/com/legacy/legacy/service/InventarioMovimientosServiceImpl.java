package com.legacy.legacy.service;

import com.legacy.legacy.model.InventarioMovimientos;
import com.legacy.legacy.repository.InventarioMovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioMovimientosServiceImpl implements InventarioMovimientosService {
    
    @Autowired
    private InventarioMovimientosRepository inventarioMovimientosRepository;
    
    @Autowired
    private SucursalService sucursalService;
    
    @Override
    public List<InventarioMovimientos> findAll() {
        // Filtrar por sucursal actual
        Integer sucursalId = sucursalService.getSucursalActualId();
        return inventarioMovimientosRepository.findAll().stream()
            .filter(m -> m.getSucursal() != null && m.getSucursal().getId().equals(sucursalId))
            .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public Optional<InventarioMovimientos> findById(Integer id) {
        Optional<InventarioMovimientos> movimientoOpt = inventarioMovimientosRepository.findById(id);
        if (movimientoOpt.isPresent()) {
            InventarioMovimientos movimiento = movimientoOpt.get();
            Integer sucursalId = sucursalService.getSucursalActualId();
            // Verificar que el movimiento pertenezca a la sucursal actual
            if (movimiento.getSucursal() != null && movimiento.getSucursal().getId().equals(sucursalId)) {
                return Optional.of(movimiento);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }
    
    @Override
    public InventarioMovimientos save(InventarioMovimientos inventarioMovimientos) {
        // Asignar sucursal automáticamente si no está asignada
        if (inventarioMovimientos.getSucursal() == null) {
            inventarioMovimientos.setSucursal(sucursalService.getSucursalActual());
        }
        return inventarioMovimientosRepository.save(inventarioMovimientos);
    }
    
    @Override
    public Optional<InventarioMovimientos> update(Integer id, InventarioMovimientos inventarioMovimientos) {
        if (!inventarioMovimientosRepository.existsById(id)) {
            return Optional.empty();
        }
        inventarioMovimientos.setId(id);
        return Optional.of(inventarioMovimientosRepository.save(inventarioMovimientos));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!inventarioMovimientosRepository.existsById(id)) {
            return false;
        }
        inventarioMovimientosRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return inventarioMovimientosRepository.existsById(id);
    }
}

