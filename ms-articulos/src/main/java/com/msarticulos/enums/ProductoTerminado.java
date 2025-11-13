package com.msarticulos.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum ProductoTerminado {

    @Schema(description = "9: Producto terminado (Buen día)")
    PRODUCTO_TERMINADO(9);

    private final int codigo;

    ProductoTerminado(int codigo) {
        this.codigo = codigo;
    }
}
