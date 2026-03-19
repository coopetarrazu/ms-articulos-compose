package com.msarticulos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrecioDto {
    private Integer codigoInterno;
    private String tipo;
    private Float impuesto;
    private Double precio;
    private Double pum;
    private String unidadMedida;
    private String pumTexto;
    private BigDecimal descuento;
}
