package com.msarticulos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloDto {

    @NotBlank
    private Integer codigoInterno;
    private String descripcionTiquete;
    private String codigoCABYS;
    private String CodigoBarra;
    private List<PrecioDto> precios;
    private PromocionDto promocion;
    private Boolean esFresco;
    private Boolean esFraccion;
    private Double cantidad;



}
