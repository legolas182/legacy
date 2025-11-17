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
        // Obtener sucursal actual (null si es admin)
        Integer sucursalId = sucursalService.getSucursalActualId();
        
        // Si es admin (sucursalId es null), devolver todas las ventas con relaciones cargadas
        if (sucursalId == null) {
            return ventasRepository.findAllWithRelations();
        }
        
        // Si no es admin, filtrar por sucursal usando consulta optimizada
        return ventasRepository.findAllBySucursalIdWithRelations(sucursalId);
    }
    
    @Override
    public Optional<Ventas> findById(Integer id) {
        Optional<Ventas> ventaOpt = ventasRepository.findById(id);
        if (ventaOpt.isPresent()) {
            Ventas venta = ventaOpt.get();
            Integer sucursalId = sucursalService.getSucursalActualId();
            
            // Si es admin (sucursalId es null), permitir acceso a cualquier venta
            if (sucursalId == null) {
                return ventaOpt;
            }
            
            // Si no es admin, verificar que la venta pertenezca a la sucursal actual
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
            try {
                var sucursalActual = sucursalService.getSucursalActual();
                if (sucursalActual != null) {
                    ventas.setSucursal(sucursalActual);
                } else {
                    // Si no hay sucursal configurada, obtener la primera sucursal activa
                    var sucursales = sucursalService.listarSucursalesActivas();
                    if (!sucursales.isEmpty()) {
                        ventas.setSucursal(sucursales.get(0));
                    } else {
                        throw new RuntimeException("No hay sucursales disponibles. Configure una sucursal primero.");
                    }
                }
            } catch (Exception e) {
                // Si falla, intentar obtener la primera sucursal activa
                var sucursales = sucursalService.listarSucursalesActivas();
                if (!sucursales.isEmpty()) {
                    ventas.setSucursal(sucursales.get(0));
                } else {
                    throw new RuntimeException("No hay sucursales disponibles. Configure una sucursal primero.");
                }
            }
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

