package com.legacy.legacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaRecienteDTO {
    private Integer id;
    private String tipo;
    private String mensaje;
    private LocalDateTime fecha;
    private LoteInfoDTO lote;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoteInfoDTO {
        private Integer id;
        private String numeroLote;
        private String producto;
        private String fechaVencimiento;
        private Integer diasRestantes;
    }
}

