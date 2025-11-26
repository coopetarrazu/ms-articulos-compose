package com.msarticulos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloDocumentos {

    private List<ArchivoDtos> archivos;
    private List<ArticuloEnlaceDto>  articuloEnlacesWeb;

}
