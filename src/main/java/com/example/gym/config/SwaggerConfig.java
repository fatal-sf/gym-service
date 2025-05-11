package com.example.gym.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    /**
     * Конфигурация swagger
     */
    @Bean
    public OpenAPI gymBookingOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API сервиса записи на тренировки в спортзал")
                        .description("API сервиса записи на тренировки в спортзал")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("ООО Спортзал")
                                .email("support@gym.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}