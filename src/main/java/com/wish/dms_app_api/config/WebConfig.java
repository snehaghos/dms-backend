package com.wish.dms_app_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${document.path}")
    private String DOCUMENT_PATH;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Specify your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true); // Allows credentials (cookies, authorization headers, etc.)
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serves files from the local directory specified
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/Users/Sneha Ghoshal/Documents/workspace-spring-tool-suite-4-4.24.0.RELEASE/dms-app-api/src/main/resources/static/uploads/");

    }
}
