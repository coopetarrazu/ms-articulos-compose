package com.msarticulos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta de error estándar")
public class Respuesta {
    @Schema(description = "Mensaje de error", example = "No se encontraron datos")
    private String mensaje;
}
