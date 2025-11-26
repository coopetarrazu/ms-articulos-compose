package com.msarticulos.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SolicitudPromocionDto {


    private Integer codigoPromocion;
    private String descripcion;
    private LocalDateTime fechaFinal;
    private String tipoPromocion;
    private List<DetallePromocionDto> detallePromocion;
}
