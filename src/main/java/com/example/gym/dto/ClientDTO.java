package com.example.gym.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientDTO {
    private Integer id;

    @NotBlank(message = "Имя обязательно")
    private String name;

    @Email(message = "Некорректный формат email. Ожидается email в формате ivanov@example.com")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Телефон должен быть в формате +7XXXYYYYYYY")
    private String phone;

    private Boolean isActive;
}