package com.legacy.legacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopProductoDTO {
    private Integer productoId;
    private String productoNombre;
    private Long unidadesVendidas;
    private BigDecimal ingresosTotales;
    private Integer stockActual;
    private String nivelStock; // BAJO, MEDIO, ALTO
}

