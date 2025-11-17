package com.legacy.legacy.controller;

import com.legacy.legacy.model.Sucursal;
import com.legacy.legacy.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@CrossOrigin(origins = "*")
public class SucursalController {
    
    @Autowired
    private SucursalService sucursalService;
    
    /**
     * Listar todas las sucursales activas
     */
    @GetMapping
    public ResponseEntity<List<Sucursal>> listarSucursalesActivas() {
        return ResponseEntity.ok(sucursalService.listarSucursalesActivas());
    }
    
    /**
     * Listar todas las sucursales (incluyendo inactivas) - SOLO ADMIN
     */
    @GetMapping("/todas")
    public ResponseEntity<List<Sucursal>> listarTodas() {
        // TODO: Verificar que el usuario actual sea ADMIN
        return ResponseEntity.ok(sucursalService.listarTodas());
    }
    
    /**
     * Obtener sucursal por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerSucursal(@PathVariable Integer id) {
        try {
            Sucursal sucursal = sucursalService.obtenerPorId(id);
            return ResponseEntity.ok(sucursal);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Obtener sucursal actual del usuario autenticado
     */
    @GetMapping("/actual")
    public ResponseEntity<Sucursal> obtenerSucursalActual() {
        try {
            Sucursal sucursal = sucursalService.getSucursalActual();
            return ResponseEntity.ok(sucursal);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

