package ru.skypro.homework.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.skypro.homework.dto.Role;

@Data
@Schema(description="Данные для регистрации")
public class Register{

@Schema(description="логин", minLength=4, maxLength=32)
@NotBlank
@Size(min=4, max=32)
private String username;

@Schema(description="пароль", minLength=8, maxLength=16)
@NotBlank
@Size(min=8, max=16)
private String password;

@Schema(description="имя пользователя", minLength=2, maxLength=16)
@NotBlank
@Size(min=2, max=16)
private String firstName;

@Schema(description="фамилия пользователя", minLength=2, maxLength=16)
@NotBlank
@Size(min=2, max=16)
private String lastName;

@Schema(description="телефон пользователя", pattern="\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
@Pattern(regexp="\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
private String phone;

@Schema(description="роль пользователя", allowableValues={"USER","ADMIN"})
private Role role;

}
