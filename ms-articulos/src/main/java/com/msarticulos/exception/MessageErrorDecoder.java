package com.msarticulos.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class MessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.body() != null) {
            try (InputStream body = response.body().asInputStream()) {
                String datos = StreamUtils.copyToString(body, StandardCharsets.UTF_8);
                JsonNode jsonNode = objectMapper.readTree(datos);
                String mensaje;
                if (jsonNode.has("mensaje")) {
                    mensaje = jsonNode.get("mensaje").asText();
                } else {
                    // Si el jsonNode es un JSON válido, enviarlo como mensaje legible
                    mensaje = jsonNode.toString();
                }
                // Obtener el HttpStatus desde el código
                org.springframework.http.HttpStatus status;
                try {
                    status = org.springframework.http.HttpStatus.valueOf(response.status());
                } catch (IllegalArgumentException e) {
                    status = org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
                }
                    return new ServiceException(mensaje, status);
            } catch (IOException exception) {
                return new Exception(exception.getMessage());
            }
        }
        return errorDecoder.decode(methodKey, response);
    }

}
