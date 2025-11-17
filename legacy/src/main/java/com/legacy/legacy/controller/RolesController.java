package com.legacy.legacy.controller;

import com.legacy.legacy.model.Roles;
import com.legacy.legacy.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {
    
    @Autowired
    private RolesService rolesService;
    
    @GetMapping
    public ResponseEntity<List<Roles>> getAll() {
        return ResponseEntity.ok(rolesService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Roles> getById(@PathVariable Integer id) {
        return rolesService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Roles> create(@RequestBody Roles roles) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rolesService.save(roles));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Roles> update(@PathVariable Integer id, @RequestBody Roles roles) {
        return rolesService.update(id, roles)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (rolesService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

