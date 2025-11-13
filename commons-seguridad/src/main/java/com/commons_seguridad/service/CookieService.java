package com.commons_seguridad.service;

import com.commons_seguridad.dao.CookieDto;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void addhttpOnlyCookie(CookieDto cookieDto, HttpServletResponse response);
}
