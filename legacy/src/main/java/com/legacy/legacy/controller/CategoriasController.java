package com.legacy.legacy.controller;

import com.legacy.legacy.model.Categorias;
import com.legacy.legacy.service.CategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriasController {
    
    @Autowired
    private CategoriasService categoriasService;
    
    @GetMapping
    public ResponseEntity<List<Categorias>> getAll() {
        return ResponseEntity.ok(categoriasService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categorias> getById(@PathVariable Integer id) {
        Optional<Categorias> categorias = categoriasService.findById(id);
        return categorias.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Categorias> create(@RequestBody Categorias categorias) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriasService.save(categorias));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Categorias> update(@PathVariable Integer id, @RequestBody Categorias categorias) {
        if (!categoriasService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        categorias.setId(id);
        return ResponseEntity.ok(categoriasService.save(categorias));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!categoriasService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        categoriasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

