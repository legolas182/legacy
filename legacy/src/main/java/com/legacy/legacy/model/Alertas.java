package com.legacy.legacy.model;

import com.legacy.legacy.model.enums.TipoAlerta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alertas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = false)
    private Lotes lote;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlerta tipo;
    
    @Column(nullable = false)
    private String mensaje;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
}

