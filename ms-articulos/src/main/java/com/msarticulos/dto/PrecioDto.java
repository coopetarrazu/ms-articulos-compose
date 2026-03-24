package com.msarticulos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Porcentaje de descuento con dos decimales", example = "20.00", pattern = "^\\d+\\.\\d{2}$")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal descuento;
}
