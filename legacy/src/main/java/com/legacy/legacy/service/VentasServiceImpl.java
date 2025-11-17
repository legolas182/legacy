package com.legacy.legacy.service;

import com.legacy.legacy.model.Ventas;
import com.legacy.legacy.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VentasServiceImpl implements VentasService {
    
    @Autowired
    private VentasRepository ventasRepository;
    
    @Autowired
    private SucursalService sucursalService;
    
    @Override
    public List<Ventas> findAll() {
        // Filtrar por sucursal actual
        Integer sucursalId = sucursalService.getSucursalActualId();
        return ventasRepository.findAll().stream()
            .filter(v -> v.getSucursal() != null && v.getSucursal().getId().equals(sucursalId))
            .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public Optional<Ventas> findById(Integer id) {
        Optional<Ventas> ventaOpt = ventasRepository.findById(id);
        if (ventaOpt.isPresent()) {
            Ventas venta = ventaOpt.get();
            Integer sucursalId = sucursalService.getSucursalActualId();
            // Verificar que la venta pertenezca a la sucursal actual
            if (venta.getSucursal() != null && venta.getSucursal().getId().equals(sucursalId)) {
                return Optional.of(venta);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }
    
    @Override
    public Ventas save(Ventas ventas) {
        // Asignar sucursal automáticamente si no está asignada
        if (ventas.getSucursal() == null) {
            ventas.setSucursal(sucursalService.getSucursalActual());
        }
        return ventasRepository.save(ventas);
    }
    
    @Override
    public Optional<Ventas> update(Integer id, Ventas ventas) {
        if (!ventasRepository.existsById(id)) {
            return Optional.empty();
        }
        ventas.setId(id);
        return Optional.of(ventasRepository.save(ventas));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!ventasRepository.existsById(id)) {
            return false;
        }
        ventasRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return ventasRepository.existsById(id);
    }
}

