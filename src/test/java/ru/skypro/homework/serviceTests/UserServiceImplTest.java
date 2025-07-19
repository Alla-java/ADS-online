package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceImplTest {
    private final UserServiceImpl userService = new UserServiceImpl();

    /**
     * Тест проверяет, что метод updatePassword выбрасывает исключение,
     * так как он ещё не реализован и содержит заглушку.
     */
    @Test
    void updatePassword_shouldThrowException() {
        NewPassword dto = new NewPassword();
        dto.setCurrentPassword("12345678");
        dto.setNewPassword("87654321");

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> userService.updatePassword(dto)
        );

        assertEquals("Метод updatePassword() не реализован", exception.getMessage());
    }

    /**
     * Тест проверяет, что метод getUser выбрасывает исключение,
     * так как реализация ещё не написана.
     */
    @Test
    void getUser_shouldThrowException() {
        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                userService::getUser
        );

        assertEquals("Метод getUser() не реализован", exception.getMessage());
    }

    /**
     * Тест проверяет, что метод updateUser выбрасывает исключение,
     * так как представляет собой заглушку.
     */
    @Test
    void updateUser_shouldThrowException() {
        UpdateUser dto = new UpdateUser("Анна", "Ивановна", "+7 (931) 123-45-67");

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> userService.updateUser(dto)
        );

        assertEquals("Метод updateUser() не реализован", exception.getMessage());
    }

    /**
     * Тест проверяет, что метод updateUserImage выбрасывает исключение,
     * так как его логика пока не реализована.
     */
    @Test
    void updateUserImage_shouldThrowException() {
        MockMultipartFile file = new MockMultipartFile(
                "image", "avatar.jpg", "image/jpeg", new byte[]{1, 2, 3}
        );

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> userService.updateUserImage(file)
        );

        assertEquals("Метод updateUserImage() не реализован", exception.getMessage());
    }
}






