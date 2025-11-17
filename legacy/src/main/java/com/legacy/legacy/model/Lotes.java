package com.legacy.legacy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "lotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lotes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos producto;
    
    @Column(name = "numero_lote", nullable = false)
    private String numeroLote;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "stock_inicial", nullable = false)
    private Integer stockInicial;
    
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;
}

