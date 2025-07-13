package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateUser{
@Size(min=3, max=10, message="Имя должно быть от 3 до 10 символов")
@Schema(description="Имя пользователя", example="Анна", minLength=3, maxLength=10)
private String firstName;

@Size(min=3, max=10, message="Фамилия должна быть от 3 до 10 символов")
@Schema(description="Фамилия пользователя", example="Иванова", minLength=3, maxLength=10)
private String lastName;

@Pattern(regexp="\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", message="Телефон должен быть в формате +7 (XXX) XXX-XX-XX")
@Schema(description="Телефон пользователя", example="+7 (931) 123-45-67")
private String phone;

}
