package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;

/**
 * Контроллер для аутентификации и регистрации пользователей.
 * Обрабатывает запросы на вход в систему и создание новых учетных записей.
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Авторизация")
public class AuthController {

    private final AuthService authService;

    /**
     * Выполняет аутентификацию пользователя
     * @param login данные для входа (email и пароль)
     * @return ResponseEntity с кодом 200 при успехе или 401 при ошибке
     */
    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        if (authService.login(login.getUsername(), login.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Регистрирует нового пользователя
     * @param register данные для регистрации
     * @return ResponseEntity с кодом 201 при успехе или 400 при ошибке
     */
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register register) {
        if (authService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
