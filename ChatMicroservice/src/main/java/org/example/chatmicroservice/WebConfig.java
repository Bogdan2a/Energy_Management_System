package org.example.chatmicroservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/chat/**") // Only allow CORS for WebSocket endpoints
                .allowedOrigins("http://localhost:4200") // Allow connections from Angular app's URL
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
