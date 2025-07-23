package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.UserServiceImpl;

import ru.skypro.homework.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ImageService imageService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setEmail("test@example.com");

        // Установка контекста безопасности
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("test@example.com", null)
        );

        when(userRepository.findUserByEmailIgnoreCase("test@example.com"))
                .thenReturn(Optional.of(mockUser));
    }

    @Test //проверка обновления пароля пользователя
    void testUpdatePassword() {
        NewPassword passwordDto = new NewPassword();
        passwordDto.setNewPassword("newSecret");

        when(passwordEncoder.encode("newSecret")).thenReturn("encodedSecret");

        userService.updatePassword(passwordDto);

        verify(userRepository).save(mockUser);
        assertEquals("encodedSecret", mockUser.getPassword());
    }

    @Test //проверка получения данных о пользователе
    void testGetUser() {
        UserDto userDto = new UserDto();
        when(userMapper.userIntoUserDto(mockUser)).thenReturn(userDto);

        UserDto result = userService.getUser();
        assertEquals(userDto, result);
    }

    @Test //проверка обновления информации о пользователе
    void testUpdateUser() {
        UpdateUser updateUser = new UpdateUser();
        doNothing().when(userMapper).updateUserIntoUser(mockUser, updateUser);

        userService.updateUser(updateUser);

        verify(userMapper).updateUserIntoUser(mockUser, updateUser);
        verify(userRepository).save(mockUser);
    }

    @Test //проверка обновления аватара пользователя
    void testUpdateUserImage() {
        MultipartFile mockFile = mock(MultipartFile.class);
        Image mockImage = new Image();
        mockImage.setId(123);
        byte[] data = "fake-image".getBytes();

        try {
            // Мокаем getBytes(), он объявлен с throws IOException, поэтому ловим исключение внутри
            when(mockFile.getBytes()).thenReturn(data);
        } catch (IOException e) {
            // Тут исключения не будет, так как мок просто возвращает данные
            throw new RuntimeException(e);
        }

        when(mockFile.getContentType()).thenReturn("image/png");
        when(imageService.uploadUserImage(mockFile)).thenReturn("/images/123");
        when(imageService.getImage(123)).thenReturn(mockImage);

        userService.updateUserImage(mockFile);

        verify(userRepository).save(mockUser);
        assertEquals(mockImage, mockUser.getImage());
    }

    @Test //проверка, если пользователь не найден в базе по email
    void testGetCurrentUserThrowsIfNotFound() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("notfound@example.com", null)
        );

        when(userRepository.findUserByEmailIgnoreCase("notfound@example.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.getUser()
        );

        assertTrue(exception.getMessage().contains("Пользователь не найден"));
    }
}






