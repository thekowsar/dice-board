package com.jkr.frontend.frontend1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("*")
                //.exposedHeaders("Trace-Id", "Request-Id", "Request-Received-Time", "Response-Processing-Time-In-Ms")
                .allowedOrigins("*");
    }

}