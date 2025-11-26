package com.msarticulos.service;

import com.msarticulos.dto.*;

import java.util.List;
import java.util.Optional;

public interface ArticuloComposeSvc {

    Optional<ArticuloDto> buscarPorCodigo(Integer unidad, String codigo);

    Optional<PaginaDto> buscarPorDescripcion(Integer unidad, String descripcion, boolean fresco, Integer pagina);

    List<ArticuloDto> inventarioBodega(Integer unidad, String codigoBodega);

    List<PrecioDto> precios(Integer unidad, String codigoBodega);

    List<ArticuloDto>  preciosCliente(Integer unidad, String tipoIdentificacion, String identificacion);

    List<CajaDto> listarCajas(Integer unidad);

    List<SolicitudPromocionDto> consultaPromociones(Integer unidad);

}
