package ru.skypro.homework.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class NewPassword {

    @Size(min = 8, max = 16, message = "Текущий пароль должен быть от 8 до 16 символов")
    @Schema(
            description = "Текущий пароль",
            example = "oldpass123",
            minLength = 8,
            maxLength = 16
    )
    private String currentPassword;

    @Size(min = 8, max = 16, message = "Новый пароль должен быть от 8 до 16 символов")
    @Schema(
            description = "Новый пароль",
            example = "newpass456",
            minLength = 8,
            maxLength = 16
    )
    private String newPassword;

    }











