package com.commons_seguridad.autoconfig;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.commons_seguridad")
@EnableFeignClients(basePackages = "com.commons_seguridad.client")
public class CommonsSeguridadAutoConfig {
}