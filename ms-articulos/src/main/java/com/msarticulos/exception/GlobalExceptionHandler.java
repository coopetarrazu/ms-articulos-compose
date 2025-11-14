package com.msarticulos.exception;

import com.msarticulos.dto.Respuesta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Respuesta> handlePersonaServiceException(ServiceException ex) {
        LOGGER.error("Error en servicio de inventario: {}", ex.getMessage(), ex);
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new Respuesta(ex.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Respuesta> handleFeignException(FeignException ex) {
        LOGGER.error("Error Feign al comunicarse con microservicio externo: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new Respuesta("Error de comunicación con servicio externo"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Respuesta> handleGenericException(Exception ex) {
        LOGGER.error("Error inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Respuesta("Error interno del servidor"));
    }
}
