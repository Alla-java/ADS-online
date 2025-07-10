package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Данные для авторизации")
public class Login {

    @Schema(description = "логин", minLength = 4, maxLength = 32)
    @NotBlank
    @Size(min = 4, max = 32)
    private String username;

    @Schema(description = "пароль", minLength = 8, maxLength = 16)
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
}
