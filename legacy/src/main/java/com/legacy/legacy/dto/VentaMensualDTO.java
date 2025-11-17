package com.legacy.legacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaMensualDTO {
    private String mes; // YYYY-MM
    private String mesNombre;
    private BigDecimal totalVentas;
}

