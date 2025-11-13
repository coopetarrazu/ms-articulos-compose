package com.msarticulos.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginaDto {
    private int total;
    private List<ArticuloDto> articulos;

    public PaginaDto() {
        this.total = 0;
        this.articulos = new ArrayList<>();
    }
}
