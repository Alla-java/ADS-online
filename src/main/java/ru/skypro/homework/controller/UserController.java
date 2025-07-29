package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Parameter;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.UserService;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Обновление пароля авторизованного пользователя")
    @PostMapping("/set_password")
    public void setPassword(
            @Valid @RequestBody NewPassword passwordDto
    ) {
        userService.updatePassword(passwordDto);
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me")
    public UserDto getCurrentUser() {
        return userService.getUser();
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @PatchMapping("/me")
    public UpdateUser updateUserInfo(
            @Valid @RequestBody UpdateUser updateDto
    ) {
        return userService.updateUser(updateDto);
    }

}