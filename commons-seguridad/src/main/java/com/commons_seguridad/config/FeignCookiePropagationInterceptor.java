package com.commons_seguridad.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class FeignCookiePropagationInterceptor implements RequestInterceptor {

    private final ObjectProvider<HttpServletRequest> requestProvider;

    @Override
    public void apply(RequestTemplate template) {
        // Validar si el cliente Feign tiene la anotación @PropagateCookies
        if (!shouldPropagateHeaders(template)) {
            return;
        }

        HttpServletRequest request = requestProvider.getIfAvailable();
        if (request == null) return;

        String apiKey = safeTrim(request.getHeader("X-API-KEY"));
        if (StringUtils.hasText(apiKey)) {
            template.header("X-API-KEY", apiKey);
            return;
        }

        String authHeader = safeTrim(request.getHeader("Authorization"));
        if (StringUtils.hasText(authHeader)) {
            template.header("Authorization", authHeader);
            return;
        }

        String cookieHeader = safeTrim(request.getHeader("Cookie"));
        if (StringUtils.hasText(cookieHeader)) {
            template.header("Cookie", cookieHeader);
        }
    }

    private boolean shouldPropagateHeaders(RequestTemplate template) {
        // Esto solo funciona si se configuran feign clients con @FeignClient y contratos Spring
        return template.feignTarget() != null &&
                template.feignTarget().type().isAnnotationPresent(PropagateHeaders.class);
    }

    private String safeTrim(String value) {
        return value != null ? value.trim() : null;
    }
}
