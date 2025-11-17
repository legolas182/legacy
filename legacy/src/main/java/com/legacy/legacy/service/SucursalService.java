package com.legacy.legacy.service;

import com.legacy.legacy.model.Sucursal;
import com.legacy.legacy.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalService {
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    // ThreadLocal para almacenar la sucursal del usuario actual en cada request
    private static final ThreadLocal<Integer> sucursalActual = new ThreadLocal<>();
    
    /**
     * Establece la sucursal actual para el thread actual
     * Se llama automáticamente en el login
     */
    public void setSucursalActual(Integer sucursalId) {
        sucursalActual.set(sucursalId);
    }
    
    /**
     * Obtiene el ID de la sucursal actual del contexto
     * @return ID de la sucursal actual
     * @throws RuntimeException si no hay sucursal configurada
     */
    public Integer getSucursalActualId() {
        Integer id = sucursalActual.get();
        if (id == null) {
            throw new RuntimeException("No hay sucursal configurada en el contexto. El usuario debe iniciar sesión.");
        }
        return id;
    }
    
    /**
     * Obtiene la sucursal actual completa
     */
    public Sucursal getSucursalActual() {
        Integer id = getSucursalActualId();
        return sucursalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }
    
    /**
     * Limpia el contexto de sucursal (útil para logout)
     */
    public void limpiarSucursalActual() {
        sucursalActual.remove();
    }
    
    /**
     * Lista todas las sucursales activas
     */
    public List<Sucursal> listarSucursalesActivas() {
        return sucursalRepository.findAll().stream()
            .filter(s -> s.getActiva() != null && s.getActiva())
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene todas las sucursales
     */
    public List<Sucursal> listarTodas() {
        return sucursalRepository.findAll();
    }
    
    /**
     * Obtiene una sucursal por ID
     */
    public Sucursal obtenerPorId(Integer id) {
        return sucursalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }
}

