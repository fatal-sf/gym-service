package com.example.gym.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TrainerDTO {
    private Integer id;

    @NotBlank(message = "Полное имя обязательно")
    @Size(max = 100, message = "Полное имя должно быть менее 100 символов")
    private String fullName;

    @NotBlank(message = "Требуется специализация")
    @Size(max = 100, message = "Специализация должна быть менее 100 символов.")
    private String specialization;

    @Size(max = 20, message = "Телефон должен быть короче 20 символов")
    private String phone;

    @Size(max = 100, message = "Электронная почта должна содержать менее 100 символов.")
    private String email;

    private Boolean isActive;
}