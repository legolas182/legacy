package com.legacy.legacy.controller;

import com.legacy.legacy.model.MetodosPago;
import com.legacy.legacy.service.MetodosPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/metodos-pago")
@CrossOrigin(origins = "*")
public class MetodosPagoController {
    
    @Autowired
    private MetodosPagoService metodosPagoService;
    
    @GetMapping
    public ResponseEntity<List<MetodosPago>> getAll() {
        return ResponseEntity.ok(metodosPagoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MetodosPago> getById(@PathVariable Integer id) {
        Optional<MetodosPago> metodosPago = metodosPagoService.findById(id);
        return metodosPago.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<MetodosPago> create(@RequestBody MetodosPago metodosPago) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(metodosPagoService.save(metodosPago));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MetodosPago> update(@PathVariable Integer id, @RequestBody MetodosPago metodosPago) {
        if (!metodosPagoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        metodosPago.setId(id);
        return ResponseEntity.ok(metodosPagoService.save(metodosPago));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!metodosPagoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        metodosPagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

