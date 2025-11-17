package com.legacy.legacy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Productos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String concentracion;
    
    @Column(name = "forma_farmaceutica")
    private String formaFarmaceutica;
    
    @Column(name = "via_administracion")
    private String viaAdministracion;
    
    @Column(name = "registro_invima")
    private String registroInvima;
    
    @Column(name = "requiere_formula")
    private Boolean requiereFormula;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categorias categoria;
    
    @ManyToOne
    @JoinColumn(name = "laboratorio_id")
    private Laboratorios laboratorio;
}

