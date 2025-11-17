package com.legacy.legacy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compras_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComprasDetalle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compras compra;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos producto;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;
    
    @Column(name = "numero_lote")
    private String numeroLote;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
}

