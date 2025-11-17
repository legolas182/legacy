package com.legacy.legacy.model;

import com.legacy.legacy.model.enums.TipoMetodoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metodos_pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodosPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMetodoPago tipo;
}

