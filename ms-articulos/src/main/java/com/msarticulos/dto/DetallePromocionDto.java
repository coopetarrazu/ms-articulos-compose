package com.msarticulos.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetallePromocionDto {

    private Integer codigoInterno;
    private BigDecimal montoDescuento;
    private BigDecimal porcentajeDescuento;
    private BigDecimal precioPropuesto;
    private BigDecimal precioVenta;

}
