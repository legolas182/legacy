package com.legacy.legacy.controller;

import com.legacy.legacy.model.InventarioMovimientos;
import com.legacy.legacy.service.InventarioMovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventario-movimientos")
@CrossOrigin(origins = "*")
public class InventarioMovimientosController {
    
    @Autowired
    private InventarioMovimientosService inventarioMovimientosService;
    
    @GetMapping
    public ResponseEntity<List<InventarioMovimientos>> getAll() {
        return ResponseEntity.ok(inventarioMovimientosService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InventarioMovimientos> getById(@PathVariable Integer id) {
        Optional<InventarioMovimientos> inventarioMovimientos = inventarioMovimientosService.findById(id);
        return inventarioMovimientos.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<InventarioMovimientos> create(@RequestBody InventarioMovimientos inventarioMovimientos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventarioMovimientosService.save(inventarioMovimientos));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InventarioMovimientos> update(@PathVariable Integer id, @RequestBody InventarioMovimientos inventarioMovimientos) {
        if (!inventarioMovimientosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        inventarioMovimientos.setId(id);
        return ResponseEntity.ok(inventarioMovimientosService.save(inventarioMovimientos));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!inventarioMovimientosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        inventarioMovimientosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

