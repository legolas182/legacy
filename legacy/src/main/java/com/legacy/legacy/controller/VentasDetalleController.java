package com.legacy.legacy.controller;

import com.legacy.legacy.model.VentasDetalle;
import com.legacy.legacy.service.VentasDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
        Optional<VentasDetalle> ventasDetalle = ventasDetalleService.findById(id);
        return ventasDetalle.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<VentasDetalle> create(@RequestBody VentasDetalle ventasDetalle) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventasDetalleService.save(ventasDetalle));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VentasDetalle> update(@PathVariable Integer id, @RequestBody VentasDetalle ventasDetalle) {
        if (!ventasDetalleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ventasDetalle.setId(id);
        return ResponseEntity.ok(ventasDetalleService.save(ventasDetalle));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!ventasDetalleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ventasDetalleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

