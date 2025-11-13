package com.commons_seguridad.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookieDto {
    private String name;
    private String value;
    private int maxAge;
}
