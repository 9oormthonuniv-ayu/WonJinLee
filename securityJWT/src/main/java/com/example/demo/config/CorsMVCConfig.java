package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMVCConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        //모든 컨트롤러 경로에 대해서 프론트 단의 경로 허용
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }
}
