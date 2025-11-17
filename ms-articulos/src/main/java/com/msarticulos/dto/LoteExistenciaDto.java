package com.msarticulos.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoteExistenciaDto {

    private String numeroLote;
    private Integer codigoArticulo;
    private BigDecimal existenciaActual;

}
