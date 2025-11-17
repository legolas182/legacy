package com.legacy.legacy.controller;

import com.legacy.legacy.model.InventarioMovimientos;
import com.legacy.legacy.service.InventarioMovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventario-movimientos")
public class InventarioMovimientosController {
    
    @Autowired
    private InventarioMovimientosService inventarioMovimientosService;
    
    @GetMapping
    public ResponseEntity<List<InventarioMovimientos>> getAll() {
        return ResponseEntity.ok(inventarioMovimientosService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InventarioMovimientos> getById(@PathVariable Integer id) {
        return inventarioMovimientosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<InventarioMovimientos> create(@RequestBody InventarioMovimientos inventarioMovimientos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventarioMovimientosService.save(inventarioMovimientos));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InventarioMovimientos> update(@PathVariable Integer id, @RequestBody InventarioMovimientos inventarioMovimientos) {
        return inventarioMovimientosService.update(id, inventarioMovimientos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (inventarioMovimientosService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

