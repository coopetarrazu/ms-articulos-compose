package com.msarticulos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal descuento;
}
