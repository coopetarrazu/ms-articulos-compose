package com.commons_seguridad.service;

import com.commons_seguridad.dao.CookieDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;


@Service
public class CookieServiceImpl  implements CookieService {
    @Override
    public void addhttpOnlyCookie(CookieDto cookieDto, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(cookieDto.getName(), cookieDto.getValue())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")   // <- crítico para cross-site
                .maxAge(cookieDto.getMaxAge())
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
