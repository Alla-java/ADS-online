package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTests{

@Mock
private UserRepository userRepository;

@Mock
private UserMapper userMapper;

@Mock
private PasswordEncoder passwordEncoder;

@Mock
private ImageService imageService;

@InjectMocks
private UserServiceImpl userService;

@Mock
private SecurityContext securityContext;

@Mock
private Authentication authentication;

private User user;
private UserDto userDto;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);

    user = new User();
    user.setEmail("test@example.com");
    user.setPassword("oldpassword");

    userDto = new UserDto();
    userDto.setEmail("test@example.com");

    // Mock security context to return the email of the current user
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn("test@example.com");
    SecurityContextHolder.setContext(securityContext);

    // Mock repository to return the user by email
    when(userRepository.findUserByEmailIgnoreCase("test@example.com"))
     .thenReturn(Optional.of(user));
}

@Test
void updatePassword_shouldEncodeAndSavePassword() {
    NewPassword newPassword = new NewPassword();
    newPassword.setNewPassword("newStrongPassword");

    when(passwordEncoder.encode("newStrongPassword")).thenReturn("encodedPassword");

    userService.updatePassword(newPassword);

    verify(userRepository).save(user);
    assertEquals("encodedPassword", user.getPassword());
}

@Test
void getUser_shouldReturnUserDto() {
    when(userMapper.userIntoUserDto(user)).thenReturn(userDto);

    UserDto result = userService.getUser();

    assertEquals(userDto, result);
}

@Test
void updateUser_shouldUpdateAndSaveUser() {

    UpdateUser updateUser = new UpdateUser();
    updateUser.setFirstName("John");
    updateUser.setLastName("Doe");
    updateUser.setPhone("+7 (123) 456-78-90");

    User testUser = new User();
    testUser.setId(1);
    testUser.setEmail("test@example.com");
    testUser.setFirstName("OldFirstName");
    testUser.setLastName("OldLastName");
    testUser.setPhone("+0 (000) 000-00-00");
    testUser.setRole(Role.USER);

    User updatedUser = new User();
    updatedUser.setId(1);
    updatedUser.setEmail("test@example.com");
    updatedUser.setFirstName("John");
    updatedUser.setLastName("Doe");
    updatedUser.setPhone("+7 (123) 456-78-90");
    updatedUser.setRole(Role.USER);

    UpdateUser expectedResult = new UpdateUser();
    expectedResult.setFirstName("John");
    expectedResult.setLastName("Doe");
    expectedResult.setPhone("+7 (123) 456-78-90");

    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn("test@example.com");
    when(userRepository.findUserByEmailIgnoreCase("test@example.com"))
            .thenReturn(Optional.of(testUser));

    when(userMapper.updateUserIntoUser(testUser, updateUser))
            .thenReturn(updatedUser);

    when(userRepository.save(updatedUser))
            .thenReturn(updatedUser);

    when(userMapper.userIntoUpdateUser(updatedUser))
            .thenReturn(expectedResult);

    UpdateUser result = userService.updateUser(updateUser);

    assertNotNull(result, "Результат не должен быть null");
    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());
    assertEquals("+7 (123) 456-78-90", result.getPhone());

    verify(userRepository).findUserByEmailIgnoreCase("test@example.com");
    verify(userMapper).updateUserIntoUser(testUser, updateUser);
    verify(userRepository).save(updatedUser);
    verify(userMapper).userIntoUpdateUser(updatedUser);
}

@Test
void getCurrentUserFromSecurityContext_whenUserNotFound_shouldThrow() {
    when(userRepository.findUserByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

    assertThrows(
     org.springframework.security.core.userdetails.UsernameNotFoundException.class,
     () -> userService.getCurrentUserFromSecurityContext()
    );
}
}