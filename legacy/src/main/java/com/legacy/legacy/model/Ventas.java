package com.legacy.legacy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ventas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Contactos cliente;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @ManyToOne
    @JoinColumn(name = "metodo_pago_id")
    private MetodosPago metodoPago;
    
    @Column(name = "total_general", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalGeneral;
    
    @Column(name = "requiere_formula")
    private Boolean requiereFormula;
    
    @Column(name = "numero_formula")
    private String numeroFormula;
}

