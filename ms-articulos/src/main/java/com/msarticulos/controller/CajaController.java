package com.msarticulos.controller;

import com.msarticulos.dto.CajaDto;
import com.msarticulos.dto.Respuesta;
import com.msarticulos.enums.UnidadComercial;
import com.msarticulos.service.ArticuloComposeSvc;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("cajas")
@RequiredArgsConstructor
@Tag(name = "Cajas", description = "Operaciones relacionadas con cajas (listado)")
public class CajaController {

    public final ArticuloComposeSvc articuloComposeSvc;

    @Operation(summary = "Listar cajas", description = "Devuelve la lista de cajas disponibles en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de cajas",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CajaDto.class))
            )
        ),
        @ApiResponse(responseCode = "404", description = "No se encontraron cajas disponibles",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Respuesta.class),
                examples = @ExampleObject(value = "{\"mensaje\": \"No se encontraron cajas disponibles\"}")
            )
        ),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Respuesta.class),
                examples = @ExampleObject(value = "{\"mensaje\": \"Error interno del servidor\"}")
            )
        )
    })
    @GetMapping("{unidad}/")
    public ResponseEntity<?> listarCajas(@Parameter(
            description = """
                            Unidad comercial que está solicitando el dato.<br><br>
                            <ul>
                                <li><b>PRODUCTO_TERMINADO</b> – Producto terminado (Buen día)</li>
                                <li><b>BODEGA_MAYORISTA</b> – Bodega Mayorista</li>
                                <li><b>FERRETERIA</b> – Ferretería Bajo San Juan</li>
                                <li><b>INSUMOS</b> – Insumos Bajo San Juan</li>
                                <li><b>INSUMOS_AGRO_CONEJO</b> – Suministros Río Conejo</li>
                                <li><b>INSUMOS_SAN_CARLOS</b> – Suministros San Carlos</li>
                                <li><b>INSUMOS_LEON_CORTES</b> – Suministros León Cortés</li>
                            </ul>
                            """
    ) @PathVariable UnidadComercial unidad) {
        List<CajaDto> cajas = articuloComposeSvc.listarCajas(unidad.getCodigo());
        if (cajas.isEmpty()) {
            return ResponseEntity.status(404).body(new Respuesta("No se encontraron cajas disponibles"));
        }
        return new ResponseEntity<Object>(cajas, HttpStatus.OK);
    }
}
