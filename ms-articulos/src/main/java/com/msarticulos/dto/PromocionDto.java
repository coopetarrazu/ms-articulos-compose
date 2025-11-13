package com.msarticulos.dto;

import lombok.Data;

@Data
public class PromocionDto {

    private String tipo;
    private Double porcentajeDescuento;
    private Double costoUltimo;
    private Double precioVenta;
    private String descripcion;
    private Double monto;
    private Double pum;
    private String unidadMedida;
    private String pumTexto;
}
