package com.commons_seguridad.config;

import com.commons_seguridad.client.AuthClientRest;
import com.commons_seguridad.dao.TokenResponseDao;
import com.commons_seguridad.service.CookieService;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    @Value("${security.api-key.header-name:X-API-KEY}")
    private String headerName;

    private final CookieService cookieService;
    private final AuthClientRest authClient;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-config",
            "/articulos/docs",
            "/articulos/swagger-ui",
            "/articulos/v3/api-docs"
    );

//    /swagger-ui/index.html
///swagger-ui/swagger-ui.css
///swagger-ui/index.css
///swagger-ui/swagger-ui-bundle.js
///swagger-ui/swagger-initializer.js
///swagger-ui/swagger-ui-standalone-preset.js
///swagger-ui/swagger-ui.css.map
///swagger-config
///swagger-ui/favicon-32x32.png

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        System.out.println(path);

        if (EXCLUDED_PATHS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(headerName);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Si hay API Key pero no es valido, cae en el FeignException
            if (StringUtils.hasText(apiKey)) {
                authClient.checkAuth();
            } else {

                TokenResponseDao respuesta = authClient.refreshToken();
                if (respuesta != null && respuesta.isActualizadas()) {
                    cookieService.addhttpOnlyCookie(respuesta.getAccess_token(), response);
                    cookieService.addhttpOnlyCookie(respuesta.getRefresh_token(), response);
                }
            }
        } catch (FeignException.Unauthorized e) {
            System.out.println("Authentication failed: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            System.out.println("An error occurred during authentication: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
