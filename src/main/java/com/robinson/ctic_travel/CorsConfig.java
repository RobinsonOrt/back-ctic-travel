package com.robinson.ctic_travel;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig {

    /*@Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedOrigins("http://localhost:4200")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
    }*/
}
