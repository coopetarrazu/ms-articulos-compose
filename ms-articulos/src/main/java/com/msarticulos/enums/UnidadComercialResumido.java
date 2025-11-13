package com.msarticulos.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum UnidadComercialResumido {

    @Schema(description = "9: Producto terminado (Buen día)")
    PRODUCTO_TERMINADO(9),
    @Schema(description = "12: Bodega Mayorista")
    BODEGA_MAYORISTA(12);

    private final int codigo;
    UnidadComercialResumido(int codigo) {
        this.codigo = codigo;
    }

}
