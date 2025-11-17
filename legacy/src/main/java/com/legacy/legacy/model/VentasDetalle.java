package com.legacy.legacy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "ventas_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentasDetalle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Ventas venta;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos producto;
    
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lotes lote;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;
    
    @Column(name = "precio_compra", precision = 10, scale = 2)
    private BigDecimal precioCompra;
    
    @Column(name = "ganancia", precision = 10, scale = 2)
    private BigDecimal ganancia;
    
    @Column(name = "porcentaje_ganancia", precision = 5, scale = 2)
    private BigDecimal porcentajeGanancia;
    
    // Método para calcular ganancia y porcentaje automáticamente
    public void calcularGanancia() {
        if (precioCompra != null && precioUnitario != null && precioCompra.compareTo(BigDecimal.ZERO) > 0) {
            // Ganancia = precio venta - precio compra
            this.ganancia = precioUnitario.subtract(precioCompra);
            
            // Porcentaje de ganancia = ((precio venta - precio compra) / precio compra) * 100
            BigDecimal diferencia = precioUnitario.subtract(precioCompra);
            this.porcentajeGanancia = diferencia
                .divide(precioCompra, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
        } else {
            this.ganancia = BigDecimal.ZERO;
            this.porcentajeGanancia = BigDecimal.ZERO;
        }
    }
}

