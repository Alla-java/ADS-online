package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.NewPassword;
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

    // Just verify that mapper updates the user entity
    doAnswer(invocation -> {
        User u = invocation.getArgument(0);
        UpdateUser dto = invocation.getArgument(1);
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setPhone(dto.getPhone());
        return null;
    }).when(userMapper).updateUserIntoUser(eq(user), eq(updateUser));

    userService.updateUser(updateUser);

    verify(userMapper).updateUserIntoUser(user, updateUser);
    verify(userRepository).save(user);

    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals("+7 (123) 456-78-90", user.getPhone());
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