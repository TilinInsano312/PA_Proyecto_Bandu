package com.banduu.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Configuracion del CORS para permitir solicitudes desde diferentes origenes.
 * Esta clase implementa WebMvcConfigurer para personalizar la configuración de CORS.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*");
    }

}
