package com.msarticulos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CajaDto {

    private Integer codigo;

    private String descripcion;

    private Integer codigoBodega;

}
