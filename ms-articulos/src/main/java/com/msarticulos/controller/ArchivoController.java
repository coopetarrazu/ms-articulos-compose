package com.msarticulos.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/archivo")
public class ArchivoController {

    @GetMapping("/descargar/{sistema}/{articulo}/{file}")
    public ResponseEntity<byte[]> proxyDescargar(
            @PathVariable String sistema,
            @PathVariable String articulo,
            @PathVariable String file) {

        String urlReal = "https://archivos.coopetarrazu.com/archivo/descargar/"
                + sistema + "/" + articulo + "/" + file;

        RestTemplate rest = new RestTemplate();

        ResponseEntity<byte[]> response = rest.getForEntity(urlReal, byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(response.getHeaders().getContentType());
        assert response.getBody() != null;
        headers.setContentLength(response.getBody().length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file + "\"");

        return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
    }
}
