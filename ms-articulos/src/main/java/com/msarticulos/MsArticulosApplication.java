package com.msarticulos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MsArticulosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsArticulosApplication.class, args);
	}

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("https://*.coopetarrazu.dev", "https://*.coopetarrazu.com", "http://localhost:4200", "http://localhost:8082"));
        config.setAllowedHeaders(List.of("x-api-key", "Content-Type", "Authorization"));
        config.addExposedHeader("*");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
