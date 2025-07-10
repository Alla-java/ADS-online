package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Данные для регистрации")
public class Register {

    @Schema(description = "логин", minLength = 4, maxLength = 32)
    @NotBlank
    @Size(min = 4, max = 32)
    private String username;

    @Schema(description = "пароль", minLength = 8, maxLength = 16)
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    @Schema(description = "имя пользователя", minLength = 2, maxLength = 16)
    @NotBlank
    @Size(min = 2, max = 16)
    private String firstName;

    @Schema(description = "фамилия пользователя", minLength = 2, maxLength = 16)
    @NotBlank
    @Size(min = 2, max = 16)
    private String lastName;

    @Schema(description = "телефон пользователя", pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    @Schema(description = "роль пользователя", allowableValues = {"USER", "ADMIN"})
    private Role role;
}
