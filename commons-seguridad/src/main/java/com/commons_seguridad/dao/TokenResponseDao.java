package com.commons_seguridad.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDao {
    private boolean actualizadas;
    private CookieDto access_token;
    private CookieDto refresh_token;
}
