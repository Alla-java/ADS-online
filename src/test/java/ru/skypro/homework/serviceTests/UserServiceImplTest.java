package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    @Spy
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

        // Мокаем репозиторий для нужного пользователя
        when(userRepository.findUserByEmailIgnoreCase("test@example.com"))
                .thenReturn(Optional.of(mockUser));

        // Создаем UserDetails для SecurityContext
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("test@example.com")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
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

        when(userMapper.updateUserIntoUser(mockUser, updateUser)).thenReturn(mockUser);

        userService.updateUser(updateUser);

        verify(userMapper).updateUserIntoUser(mockUser, updateUser);
        verify(userRepository).save(mockUser);
    }

    @Test
    void testUpdateUserImage() throws IOException {
        // Подготовка мок-файла
        MultipartFile mockFile = mock(MultipartFile.class);
        byte[] data = "fake-image".getBytes();

        when(mockFile.getBytes()).thenReturn(data);
        when(mockFile.getContentType()).thenReturn("image/png");

        // Подготовка изображения
        Image newImage = new Image();
        newImage.setId(123);
        newImage.setData(data);
        newImage.setMediaType("image/png");

        // Мокаем текущего пользователя без изображения
        User mockUser = new User();
        doReturn(mockUser).when(userService).getCurrentUserFromSecurityContext();

        // Мокаем создание и сохранение изображения
        when(imageService.updateImage(mockFile)).thenReturn(newImage);

        // Запуск тестируемого метода
        userService.updateUserImage(mockFile);

        // Проверки
        verify(imageService).updateImage(mockFile);
        verify(userRepository).save(mockUser);
        assertEquals(newImage, mockUser.getImage());
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






