package com.commons_seguridad.client;


import com.commons_seguridad.config.PropagateHeaders;
import com.commons_seguridad.dao.TokenResponseDao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@PropagateHeaders
@FeignClient(name = "ms-auth", url = "${SEGURIDAD.host}")
public interface AuthClientRest {

    @PostMapping("/auth/refresh")
    TokenResponseDao refreshToken();

    @GetMapping("/auth/check-Auth")
    String checkAuth();
}
