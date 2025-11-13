package com.msarticulos.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum UnidadComercial {
    @Schema(description = "9: Producto terminado (Buen día)")
    PRODUCTO_TERMINADO(9),
    @Schema(description = "12: Bodega Mayorista")
    BODEGA_MAYORISTA(12),
    @Schema(description = "30: Ferretería Bajo San Juan")
    FERRETERIA(30),
    @Schema(description = "31: Insumos Bajo San Juan")
    INSUMOS(31),
    @Schema(description = "32: Suministros Río Conejo")
    INSUMOS_AGRO_CONEJO(32),
    @Schema(description = "6: Suministros San Carlos")
    INSUMOS_SAN_CARLOS(6),
    @Schema(description = "15: Suministros León Cortés")
    INSUMOS_LEON_CORTES(15);

    private final int codigo;

    UnidadComercial(int codigo) {
        this.codigo = codigo;
    }
}
