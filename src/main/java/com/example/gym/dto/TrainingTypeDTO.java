package com.example.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TrainingTypeDTO {
    private Long id;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 100, message = "Имя должно быть менее 100 символов")
    private String name;

    @Size(max = 500, message = "Описание должно быть менее 500 символов")
    private String description;
}