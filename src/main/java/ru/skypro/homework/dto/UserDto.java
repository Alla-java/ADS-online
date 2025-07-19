package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDto {

    @Schema(description = "ID пользователя", example = "1")
    private Integer id;

    @Schema(description = "Email пользователя", example = "anna@example.com")
    private String email;

    @Schema(description = "Имя пользователя", example = "Анна")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванова")
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+7 (931) 123-45-67")
    private String phone;

    @Schema(description = "Роль пользователя", example = "USER")
    private Role role;

    @Schema(description = "Ссылка на аватар пользователя", example = "https://example.com/avatar.jpg")
    private String image; //ссылка на аватар пользователя

    public UserDto(Integer id, String email, String firstName, String lastName, String phone, Role role, String image) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.image = image;
    }
    }


