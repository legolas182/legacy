package com.legacy.legacy.model;

import com.legacy.legacy.model.enums.TipoCategoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categorias {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCategoria tipo;
}

