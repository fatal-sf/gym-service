package com.example.gym.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    /**
     * Конфигурация маппера для преобразования между сущностями и DTO
     */

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}