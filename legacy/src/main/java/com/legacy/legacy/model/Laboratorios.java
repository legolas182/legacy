package com.legacy.legacy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "laboratorios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laboratorios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(name = "pais_origen")
    private String paisOrigen;
    
    @Column(name = "registro_sanitario")
    private String registroSanitario;
}

