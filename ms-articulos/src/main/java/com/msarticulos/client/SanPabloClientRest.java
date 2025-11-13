package com.msarticulos.client;


import com.commons_seguridad.config.PropagateHeaders;
import com.msarticulos.dto.ArticuloDto;
import com.msarticulos.dto.CajaDto;
import com.msarticulos.dto.PaginaDto;
import com.msarticulos.dto.PrecioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@PropagateHeaders
@FeignClient(name = "ms-sanpablo-compose-svc", url = "${SANPABLO}")
public interface SanPabloClientRest {

    @GetMapping("/articulo/descripcion")
    Optional<PaginaDto> buscarPorDescripcion(@RequestParam(value = "descripcion", required = false) String descripcion,
                                             @RequestParam(value = "fresco", required = false) boolean fresco,
                                             @RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina);

    @GetMapping("/articulo/codigo")
    Optional<ArticuloDto> buscarPorCodigo(@RequestParam(value = "codigo", required = false) String codigo);

    @GetMapping("/articulo/inventarioBodega")
    List<ArticuloDto> cargarArticulos(@RequestParam(value = "bodega", required = false) String bodega);

    @GetMapping("/articulo/precios")
    List<PrecioDto> cargarPrecios(@RequestParam(value = "bodega", required = false) String bodega);

    @GetMapping("/articulo/precioCliente")
    List<ArticuloDto> precioCliente(@RequestParam(value = "tipoIdentificacion") String tipoIdentificacion,
                                    @RequestParam(value = "identificacion") String identificacion);

    @GetMapping("/caja/listar")
    List<CajaDto> listarCajas();

}
