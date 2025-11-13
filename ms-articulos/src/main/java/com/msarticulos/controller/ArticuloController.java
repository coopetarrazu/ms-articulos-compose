package com.msarticulos.controller;

import com.msarticulos.dto.ArticuloDto;
import com.msarticulos.dto.PaginaDto;
import com.msarticulos.dto.PrecioDto;
import com.msarticulos.dto.Respuesta;
import com.msarticulos.enums.ProductoTerminado;
import com.msarticulos.enums.UnidadComercial;
import com.msarticulos.service.ArticuloComposeSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController("articulos")
@RequiredArgsConstructor
@Tag(name = "Artículos", description = "Operaciones relacionadas con artículos (búsqueda, inventario y precios)")
public class ArticuloController {

    public final ArticuloComposeSvc articuloComposeSvc;

    @Operation(summary = "Buscar artículo por código", description = "Busca un artículo por su código numérico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artículo encontrado",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ArticuloDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No se encontraron datos para el artículo especificado",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"No se encontraron datos para el articulo especificado.\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"Error interno del servidor\"}"
                            )
                    )
            )
    })
    @GetMapping("{unidad}/codigo")
    public ResponseEntity<?> buscarPorCodigo(@Parameter(
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
                                             )
                                             @PathVariable UnidadComercial unidad,
                                             @Parameter(description = "Código numérico del artículo", required = true)
                                             @RequestParam(name = "codigo") String codigo) {

        if (unidad == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Debe indicar un unidad para continuar"));
        }

        if (codigo == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Debe indicar un codigo para continuar"));
        }
        Optional<ArticuloDto> articulo = articuloComposeSvc.buscarPorCodigo(unidad.getCodigo(), codigo);
        if (articulo.isEmpty()) {
            return ResponseEntity.status(404).body(new Respuesta("No se encontraron datos para el articulo especificado."));
        }
        return new ResponseEntity<Object>(articulo, HttpStatus.OK);
    }

    @Operation(summary = "Buscar artículos por descripción", description = "Busca artículos por una descripción parcial o completa. Permite filtrar por si son frescos y paginar resultados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PaginaDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No se encontraron datos con esta descripción",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"No se encontraron datos con esta descripción.\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"Error interno del servidor\"}"
                            )
                    )
            )
    })
    @GetMapping("{unidad}/descripcion")
    public ResponseEntity<?> buscarPorDescripcion(
            @Parameter(
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
            )
            @PathVariable UnidadComercial unidad,
            @Parameter(description = "Texto a buscar en la descripción del artículo", required = true) @RequestParam(value = "descripcion", required = false) String descripcion,
            @Parameter(description = "Filtrar sólo productos frescos") @RequestParam(value = "fresco", required = false) boolean fresco,
            @Parameter(description = "Página de resultados (0-based)") @RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina) {

        if (unidad == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Debe indicar un unidad para continuar"));
        }

        if (descripcion == null || descripcion.isBlank()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Debe indicar una descripción para buscar"));
        }
        Optional<PaginaDto> articulo = articuloComposeSvc.buscarPorDescripcion(unidad.getCodigo(), descripcion, fresco, pagina);
        if (articulo.isEmpty()) {
            return ResponseEntity.status(404).body(new Respuesta("No se encontraron datos con esta descripción."));
        }
        return new ResponseEntity<Object>(articulo, HttpStatus.OK);
    }

    @Operation(summary = "Inventario por bodega", description = "Carga la lista de artículos con existencias en una bodega específica. Si no se indica bodega, usa la bodega por defecto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de artículos",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ArticuloDto.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No se encontraron artículos en la bodega indicada",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"No se encontraron artículos en la bodega indicada.\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"Error interno del servidor\"}"
                            )
                    )
            )
    })
    @GetMapping("{unidad}/articulos")
    public ResponseEntity<?> cargarArticulos(
            @Parameter(
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
            )
            @PathVariable UnidadComercial unidad,
            @Parameter(description = "Código o nombre de la bodega")
            @RequestParam(value = "bodega", required = false) String bodega) {

        List<ArticuloDto> articulos = articuloComposeSvc.inventarioBodega(unidad.getCodigo(), bodega);
        if (articulos.isEmpty()) {
            return ResponseEntity.status(404).body(new Respuesta("No se encontraron artículos en la bodega indicada."));
        }
        return new ResponseEntity<Object>(articulos, HttpStatus.OK);
    }

    @Operation(summary = "Carga de precios por bodega", description = "Devuelve la lista de precios de artículos para la bodega indicada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de precios",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PrecioDto.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No se encontraron artículos en la bodega indicada",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"No se encontraron artículos en la bodega indicada.\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"Error interno del servidor\"}"
                            )
                    )
            )
    })
    @GetMapping("{unidad}/precios")
    public ResponseEntity<?> cargarPrecios(@Parameter(
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
                                           )
                                           @PathVariable UnidadComercial unidad,
                                           @Parameter(description = "Código o nombre de la bodega")
                                           @RequestParam(value = "bodega", required = false) String bodega) {

        if (unidad == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Debe indicar un unidad para continuar"));
        }

        List<PrecioDto> precios = articuloComposeSvc.precios(unidad.getCodigo(), bodega);
        if (precios.isEmpty()) {
            return ResponseEntity.status(404).body(new Respuesta("No se encontraron artículos en la bodega indicada."));
        }
        return new ResponseEntity<Object>(precios, HttpStatus.OK);
    }

    @Operation(summary = "Precios para cliente", description = "Carga los precios especificos aplicables a un cliente para el departamento de Producto Terminado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de artículos con precios para el cliente",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ArticuloDto.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No se encontraron artículos para el cliente indicado",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"No se encontraron artículos en la bodega indicada.\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Respuesta.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"mensaje\": \"Error interno del servidor\"}"
                            )
                    )
            )
    })
    @GetMapping("{unidad}/preciosCliente")
    public ResponseEntity<?> precioCliente(
            @Parameter(
                    description = """
                            Unidad comercial que está solicitando el dato.<br><br>
                            <ul>
                                <li><b>PRODUCTO_TERMINADO</b> – Producto terminado (Buen día)</li>
                            </ul>
                            """
            )
            @PathVariable ProductoTerminado unidad,
            @Parameter(description = """
                    <b>Tipo de identificación</b> que queremos consultar.<br>
                    <ul>
                        <li><b>01</b> = Física</li>
                        <li><b>02</b> = Jurídica</li>
                        <li><b>03</b> = DIMEX</li>
                        <li><b>04</b> = NITE</li>
                        <li><b>05</b> = Extranjero</li>
                    </ul>
                    """
            )
            @RequestParam(value = "tipoIdentificacion") String tipoIdentificacion,
            @Parameter(description = "Identificación de la persona que queremos consultar") @RequestParam(value = "identificacion") String identificacion) {

        if (unidad == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Debe indicar un unidad para continuar"));
        }

        if (tipoIdentificacion == null || tipoIdentificacion.isBlank()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Debe indicar un tipo de identificación para continuar."));
        }

        if (identificacion == null || identificacion.isBlank()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Debe indicar una identificación para continuar."));
        }

        List<ArticuloDto> preciosCliente = articuloComposeSvc.preciosCliente(unidad.getCodigo(), tipoIdentificacion, identificacion);
        if (preciosCliente.isEmpty()) {
            return ResponseEntity.status(404).body(new Respuesta("No se encontraron artículos en la bodega indicada."));
        }
        return new ResponseEntity<Object>(preciosCliente, HttpStatus.OK);
    }
}
