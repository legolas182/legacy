package com.legacy.legacy.controller;

import com.legacy.legacy.model.VentasDetalle;
import com.legacy.legacy.service.VentasDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas-detalle")
@CrossOrigin(origins = "*")
public class VentasDetalleController {
    
    @Autowired
    private VentasDetalleService ventasDetalleService;
    
    @GetMapping
    public ResponseEntity<List<VentasDetalle>> getAll() {
        return ResponseEntity.ok(ventasDetalleService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VentasDetalle> getById(@PathVariable Integer id) {
        return ventasDetalleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<VentasDetalle> create(@RequestBody VentasDetalle ventasDetalle) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventasDetalleService.save(ventasDetalle));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VentasDetalle> update(@PathVariable Integer id, @RequestBody VentasDetalle ventasDetalle) {
        return ventasDetalleService.update(id, ventasDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (ventasDetalleService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

