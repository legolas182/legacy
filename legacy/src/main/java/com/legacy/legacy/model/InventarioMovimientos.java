package com.legacy.legacy.model;

import com.legacy.legacy.model.enums.OrigenMovimiento;
import com.legacy.legacy.model.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario_movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioMovimientos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = false)
    private Lotes lote;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipo;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrigenMovimiento origen;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
}

