package com.legacy.legacy.controller;

import com.legacy.legacy.model.Lotes;
import com.legacy.legacy.service.LotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lotes")
@CrossOrigin(origins = "*")
public class LotesController {
    
    @Autowired
    private LotesService lotesService;
    
    @GetMapping
    public ResponseEntity<List<Lotes>> getAll() {
        return ResponseEntity.ok(lotesService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Lotes> getById(@PathVariable Integer id) {
        return lotesService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Lotes> create(@RequestBody Lotes lotes) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lotesService.save(lotes));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Lotes> update(@PathVariable Integer id, @RequestBody Lotes lotes) {
        return lotesService.update(id, lotes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (lotesService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

