package com.msarticulos.service;

import com.msarticulos.client.*;
import com.msarticulos.dto.ArticuloDto;
import com.msarticulos.dto.CajaDto;
import com.msarticulos.dto.PaginaDto;
import com.msarticulos.dto.PrecioDto;
import com.msarticulos.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticuloComposeSvcImpl implements ArticuloComposeSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticuloComposeSvcImpl.class);
    private final FerreteriaBSJClientRest ferreSanJuanClient;
    private final InsumosBSJClientRest insumosSanJuanClient;
    private final BodegaClientRest bodegaClientRest;
    private final BuenDiaClientRest buenDiaClient;
    private final RioConejoClientRest rioConejoClient;
    private final SanCarlosClientRest sanCarlosClient;
    private final SanPabloClientRest sanPabloClient;

    @Override
    public Optional<ArticuloDto> buscarPorCodigo(Integer unidad, String codigo) {

        try {

            switch (unidad) {

                case 6 -> {

                    Optional<ArticuloDto> articulo = sanCarlosClient.buscarPorCodigo(codigo);
                    if (articulo.isEmpty()) {
                        LOGGER.error("Error al consultar la información en la unidad San Carlos del articulo con el codigo: {}", codigo);
                        throw new ServiceException("Error al consultar la información del articulo: " + codigo, HttpStatus.NOT_FOUND);
                    }
                    return articulo;
                }

                case 9 -> {

                    Optional<ArticuloDto> articulo = buenDiaClient.buscarPorCodigo(codigo);
                    if (articulo.isEmpty()) {
                        LOGGER.error("Error al consultar la información en la unidad Producto Terminado del articulo con el codigo: {}", codigo);
                        throw new ServiceException("Error al consultar la información del articulo: " + codigo, HttpStatus.NOT_FOUND);
                    }

                    return articulo;
                }

                case 12 -> {

                    Optional<ArticuloDto> articulo = bodegaClientRest.buscarPorCodigo(codigo);
                    if (articulo.isEmpty()) {
                        LOGGER.error("Error al consultar la información en la unidad Bodega Mayorista del articulo con el codigo: {}", codigo);
                        throw new ServiceException("Error al consultar la información del articulo: " + codigo, HttpStatus.NOT_FOUND);
                    }

                    return articulo;
                }

                case 15 -> {

                    Optional<ArticuloDto> articulo = sanPabloClient.buscarPorCodigo(codigo);
                    if (articulo.isEmpty()) {
                        LOGGER.error("Error al consultar la información en la unidad San Pablo del articulo con el codigo: {}", codigo);
                        throw new ServiceException("Error al consultar la información del articulo: " + codigo, HttpStatus.NOT_FOUND);
                    }
                    return articulo;
                }


                case 30 -> {

                    Optional<ArticuloDto> articulo = insumosSanJuanClient.buscarPorCodigo(codigo);
                    if (articulo.isEmpty()) {
                        LOGGER.error("Error al consultar la información en la unidad Insumos Bajo San Juan del articulo con el codigo: {}", codigo);
                        throw new ServiceException("Error al consultar la información del articulo: " + codigo, HttpStatus.NOT_FOUND);
                    }

                    return articulo;
                }


                case 31 -> {

                    Optional<ArticuloDto> articulo = ferreSanJuanClient.buscarPorCodigo(codigo);
                    if (articulo.isEmpty()) {
                        LOGGER.error("Error al consultar la información en la unidad  Ferreteria Bajo San Juan del articulo con el codigo: {}", codigo);
                        throw new ServiceException("Error al consultar la información del articulo: " + codigo, HttpStatus.NOT_FOUND);
                    }

                    return articulo;
                }

                case 32 -> {

                    Optional<ArticuloDto> articulo = rioConejoClient.buscarPorCodigo(codigo);
                    if (articulo.isEmpty()) {
                        LOGGER.error("Error al consultar la información en la unidad Rio Conejo del articulo con el codigo: {}", codigo);
                        throw new ServiceException("Error al consultar la información del articulo: " + codigo, HttpStatus.NOT_FOUND);
                    }

                    return articulo;
                }

                default ->
                        throw new ServiceException("Error al consultar la información del articulo: ", HttpStatus.NOT_FOUND);

            }
        } catch (feign.FeignException e) {
            LOGGER.error("Error Feign al comunicarse al servicio inventarioClient.buscarPorCodigo: {}", e.getMessage());
            throw new ServiceException("Error de comunicación con el servicio de consulta de articulos.", HttpStatus.BAD_GATEWAY);

        } catch (ServiceException ex) {
            throw ex;
        } catch (RuntimeException e) {
            LOGGER.error("Error inesperado al consultar el articulo: {}", e.getMessage(), e);
            throw new ServiceException("Error interno al consultar el articulo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<PaginaDto> buscarPorDescripcion(Integer unidad, String descripcion, boolean fresco, Integer pagina) {

        try {

            switch (unidad) {

                case 6 -> {

                    Optional<PaginaDto> articulos = sanCarlosClient.buscarPorDescripcion(descripcion, fresco, pagina);
                    if (articulos.isEmpty()) {
                        LOGGER.error("Error al consultar la información en San Carlos del articulo con la descripcion: {}", descripcion);
                        throw new ServiceException("Error al consultar la información del articulo: " + descripcion, HttpStatus.NOT_FOUND);
                    }

                    return articulos;
                }

                case 9 -> {

                    Optional<PaginaDto> articulos = buenDiaClient.buscarPorDescripcion(descripcion, fresco, pagina);
                    if (articulos.isEmpty()) {
                        LOGGER.error("Error al consultar la información en Producto Terminado del articulo con la descripcion: {}", descripcion);
                        throw new ServiceException("Error al consultar la información del articulo: " + descripcion, HttpStatus.NOT_FOUND);
                    }

                    return articulos;
                }

                case 12 -> {

                    Optional<PaginaDto> articulos = bodegaClientRest.buscarPorDescripcion(descripcion, fresco, pagina);
                    if (articulos.isEmpty()) {
                        LOGGER.error("Error al consultar la información en Bodega Mayorista del articulo con la descripcion: {}", descripcion);
                        throw new ServiceException("Error al consultar la información del articulo: " + descripcion, HttpStatus.NOT_FOUND);
                    }

                    return articulos;
                }

                case 15 -> {

                    Optional<PaginaDto> articulos = sanPabloClient.buscarPorDescripcion(descripcion, fresco, pagina);
                    if (articulos.isEmpty()) {
                        LOGGER.error("Error al consultar la información en San Pablo del articulo con la descripcion: {}", descripcion);
                        throw new ServiceException("Error al consultar la información del articulo: " + descripcion, HttpStatus.NOT_FOUND);
                    }

                    return articulos;
                }

                case 30 -> {

                    Optional<PaginaDto> articulos = insumosSanJuanClient.buscarPorDescripcion(descripcion, fresco, pagina);
                    if (articulos.isEmpty()) {
                        LOGGER.error("Error al consultar la información en Insumos Bajo San Juan del articulo con la descripcion: {}", descripcion);
                        throw new ServiceException("Error al consultar la información del articulo: " + descripcion, HttpStatus.NOT_FOUND);
                    }

                    return articulos;
                }


                case 31 -> {

                    Optional<PaginaDto> articulos = ferreSanJuanClient.buscarPorDescripcion(descripcion, fresco, pagina);
                    if (articulos.isEmpty()) {
                        LOGGER.error("Error al consultar la información en Ferreteria Bajo San Juan del articulo con la descripcion: {}", descripcion);
                        throw new ServiceException("Error al consultar la información del articulo: " + descripcion, HttpStatus.NOT_FOUND);
                    }

                    return articulos;
                }


                case 32 -> {

                    Optional<PaginaDto> articulos = rioConejoClient.buscarPorDescripcion(descripcion, fresco, pagina);
                    if (articulos.isEmpty()) {
                        LOGGER.error("Error al consultar la información en Rio Conejo del articulo con la descripcion: {}", descripcion);
                        throw new ServiceException("Error al consultar la información del articulo: " + descripcion, HttpStatus.NOT_FOUND);
                    }

                    return articulos;
                }

                default ->
                        throw new ServiceException("Error al consultar la información del articulo: ", HttpStatus.NOT_FOUND);

            }

        } catch (feign.FeignException e) {
            LOGGER.error("Error Feign al comunicarse con el servicio inventarioClient.buscarPorDescripcion: {}", e.getMessage());
            throw new ServiceException("Error de comunicación con el servicio de consulta de articulos.", HttpStatus.BAD_GATEWAY);

        } catch (ServiceException ex) {
            throw ex;
        } catch (RuntimeException e) {
            LOGGER.error("Error inesperado al consultar el articulo: {}", e.getMessage(), e);
            throw new ServiceException("Error interno al consultar el articulo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ArticuloDto> inventarioBodega(Integer unidad, String codigoBodega) {

        try {

            switch (unidad) {

                case 6 -> {

                    List<ArticuloDto> inventario = sanCarlosClient.cargarArticulos(codigoBodega);
                    if (inventario.isEmpty()) {
                        LOGGER.error("Error al cargar el inventario de articulos en San Carlos para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de inventario de articulos de la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return inventario;
                }

                case 9 -> {

                    List<ArticuloDto> inventario = buenDiaClient.cargarArticulos(codigoBodega);
                    if (inventario.isEmpty()) {
                        LOGGER.error("Error al cargar el inventario de articulos en Producto Terminado para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de inventario de articulos de la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return inventario;
                }

                case 12 -> {

                    List<ArticuloDto> inventario = bodegaClientRest.cargarArticulos(codigoBodega);
                    if (inventario.isEmpty()) {
                        LOGGER.error("Error al cargar el inventario de articulos en Bodega Mayorista para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de inventario de articulos de la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return inventario;
                }

                case 15 -> {

                    List<ArticuloDto> inventario = sanPabloClient.cargarArticulos(codigoBodega);
                    if (inventario.isEmpty()) {
                        LOGGER.error("Error al cargar el inventario de articulos para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de inventario de articulos de la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return inventario;
                }

                case 30 -> {

                    List<ArticuloDto> inventario = insumosSanJuanClient.cargarArticulos(codigoBodega);
                    if (inventario.isEmpty()) {
                        LOGGER.error("Error al cargar el inventario de articulos en Insumos Bajo San Juan para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de inventario de articulos de la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return inventario;
                }

                case 31 -> {

                    List<ArticuloDto> inventario = ferreSanJuanClient.cargarArticulos(codigoBodega);
                    if (inventario.isEmpty()) {
                        LOGGER.error("Error al cargar el inventario de articulos en Ferreteria Bajo San Juan para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de inventario de articulos de la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return inventario;
                }


                case 32 -> {

                    List<ArticuloDto> inventario = rioConejoClient.cargarArticulos(codigoBodega);
                    if (inventario.isEmpty()) {
                        LOGGER.error("Error al cargar el inventario de articulos en Rio Conejo para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de inventario de articulos de la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return inventario;
                }

                default ->
                        throw new ServiceException("Error al consultar la información de inventario: ", HttpStatus.NOT_FOUND);

            }

        } catch (feign.FeignException e) {
            LOGGER.error("Error Feign al comunicarse los servicio de inventarioClient.cargarArticulos: {}", e.getMessage());
            throw new ServiceException("Error de comunicación con el servicio de consulta de inventario.", HttpStatus.BAD_GATEWAY);

        } catch (ServiceException ex) {
            throw ex;
        } catch (RuntimeException e) {
            LOGGER.error("Error inesperado al consultar el inventario en bodega: {}", e.getMessage(), e);
            throw new ServiceException("Error interno al consultar el inventario en bodega.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PrecioDto> precios(Integer unidad, String codigoBodega) {

        try {

            switch (unidad) {

                case 6 -> {

                    List<PrecioDto> precios = sanCarlosClient.cargarPrecios(codigoBodega);
                    if (precios.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en San Carlos para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de lista de precios para la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return precios;
                }

                case 9 -> {

                    List<PrecioDto> precios = buenDiaClient.cargarPrecios(codigoBodega);
                    if (precios.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en Producto Terminado para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de lista de precios para la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return precios;
                }

                case 12 -> {

                    List<PrecioDto> precios = bodegaClientRest.cargarPrecios(codigoBodega);
                    if (precios.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en Bodega Mayorista para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de lista de precios para la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return precios;
                }

                case 15 -> {

                    List<PrecioDto> precios = sanPabloClient.cargarPrecios(codigoBodega);
                    if (precios.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en San Pablo para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de lista de precios para la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return precios;
                }

                case 30 -> {

                    List<PrecioDto> precios = insumosSanJuanClient.cargarPrecios(codigoBodega);
                    if (precios.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en Insusmos Bajo San Juan para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de lista de precios para la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return precios;
                }

                case 31 -> {

                    List<PrecioDto> precios = ferreSanJuanClient.cargarPrecios(codigoBodega);
                    if (precios.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en Ferreteria Bajo San Juan para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de lista de precios para la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return precios;
                }

                case 32 -> {

                    List<PrecioDto> precios = rioConejoClient.cargarPrecios(codigoBodega);
                    if (precios.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en Rio Conejo para la bodega: {}", codigoBodega);
                        throw new ServiceException("Error al consultar la información de lista de precios para la bodega: " + codigoBodega, HttpStatus.NOT_FOUND);
                    }

                    return precios;
                }

                default ->
                        throw new ServiceException("Error al consultar la información de lista de precios: ", HttpStatus.NOT_FOUND);

            }

        } catch (feign.FeignException e) {
            LOGGER.error("Error Feign al comunicarse los servicio de inventarioClient.cargarPrecios: {}", e.getMessage());
            throw new ServiceException("Error de comunicación con el servicio de consulta de precios.", HttpStatus.BAD_GATEWAY);

        } catch (ServiceException ex) {
            throw ex;
        } catch (RuntimeException e) {
            LOGGER.error("Error inesperado al consultar los precios en bodega: {}", e.getMessage(), e);
            throw new ServiceException("Error interno al consultar los precios en bodega.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ArticuloDto> preciosCliente(Integer unidad, String tipoIdentificacion, String identificacion) {

        try {

            switch (unidad) {

                case 9 -> {

                    List<ArticuloDto> precioCliente = buenDiaClient.precioCliente(tipoIdentificacion, identificacion);
                    if (precioCliente.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en Producto Terminado para el cliente: {}", identificacion);
                        throw new ServiceException("Error al consultar la información precios del cliente: " + identificacion, HttpStatus.NOT_FOUND);
                    }

                    return precioCliente;

                }

                case 12 -> {

                    List<ArticuloDto> precioCliente = bodegaClientRest.precioCliente(tipoIdentificacion, identificacion);
                    if (precioCliente.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de precios en Bodega Mayorista para el cliente: {}", identificacion);
                        throw new ServiceException("Error al consultar la información precios del cliente: " + identificacion, HttpStatus.NOT_FOUND);
                    }

                    return precioCliente;

                }

                default ->
                        throw new ServiceException("Error, la unidad seleccionada no cuenta con precios especificos para sus clientes: ", HttpStatus.BAD_REQUEST);

            }

        } catch (feign.FeignException e) {
            LOGGER.error("Error Feign al comunicarse los servicio de inventarioClient.precioCliente: {}", e.getMessage());
            throw new ServiceException("Error de comunicación con el servicio de precios por cliente.", HttpStatus.BAD_GATEWAY);

        } catch (ServiceException ex) {
            throw ex;
        } catch (RuntimeException e) {
            LOGGER.error("Error inesperado al consultar los precios del cliente: {}", e.getMessage(), e);
            throw new ServiceException("Error interno al consultar los precios del cliente.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CajaDto> listarCajas(Integer unidad) {

        try {

            switch (unidad) {

                case 6 -> {

                    List<CajaDto> cajas = sanCarlosClient.listarCajas();
                    if (cajas.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de cajas en San Carlos");
                        throw new ServiceException("Error al consultar la información de cajas", HttpStatus.NOT_FOUND);
                    }
                    return cajas;
                }

                case 9 -> {

                    List<CajaDto> cajas = buenDiaClient.listarCajas();
                    if (cajas.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de cajas en Producto Terminado");
                        throw new ServiceException("Error al consultar la información de cajas", HttpStatus.NOT_FOUND);
                    }
                    return cajas;
                }

                case 12 -> {

                    List<CajaDto> cajas = bodegaClientRest.listarCajas();
                    if (cajas.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de cajas en Bodega Mayorista");
                        throw new ServiceException("Error al consultar la información de cajas", HttpStatus.NOT_FOUND);
                    }
                    return cajas;
                }

                case 15 -> {

                    List<CajaDto> cajas = sanPabloClient.listarCajas();
                    if (cajas.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de cajas en San Pablo");
                        throw new ServiceException("Error al consultar la información de cajas", HttpStatus.NOT_FOUND);
                    }
                    return cajas;
                }

                case 30 -> {

                    List<CajaDto> cajas = insumosSanJuanClient.listarCajas();
                    if (cajas.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de cajas en Insumos Bajo San Juan");
                        throw new ServiceException("Error al consultar la información de cajas", HttpStatus.NOT_FOUND);
                    }
                    return cajas;
                }

                case 31 -> {

                    List<CajaDto> cajas = ferreSanJuanClient.listarCajas();
                    if (cajas.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de cajas en Ferreteria Bajo San Juan");
                        throw new ServiceException("Error al consultar la información de cajas", HttpStatus.NOT_FOUND);
                    }
                    return cajas;
                }

                case 32 -> {

                    List<CajaDto> cajas = rioConejoClient.listarCajas();
                    if (cajas.isEmpty()) {
                        LOGGER.error("Error al cargar la lista de cajas en Rio Conejo");
                        throw new ServiceException("Error al consultar la información de cajas", HttpStatus.NOT_FOUND);
                    }
                    return cajas;
                }

                default ->
                        throw new ServiceException("Error al consultar la información de lista de precios: ", HttpStatus.NOT_FOUND);

            }

        } catch (feign.FeignException e) {
            LOGGER.error("Error Feign al comunicarse los servicio de inventarioClient.listarCajas: {}", e.getMessage());
            throw new ServiceException("Error de comunicación con el servicio de consulta de cajas.", HttpStatus.BAD_GATEWAY);

        } catch (ServiceException ex) {
            throw ex;
        } catch (RuntimeException e) {
            LOGGER.error("Error inesperado al consultar las cajas disponibles: {}", e.getMessage(), e);
            throw new ServiceException("Error interno al consultar las cajas disponibles.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
