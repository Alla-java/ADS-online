package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

@Mock
private PasswordEncoder passwordEncoder;

@Mock
private UserRepository userRepository;

@InjectMocks
private AuthServiceImpl authService;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void login_shouldReturnTrue_whenPasswordMatches() {
    String username = "user@example.com";
    String rawPassword = "password";
    String encodedPassword = "encoded";

    User user = new User();
    user.setPassword(encodedPassword);

    when(userRepository.findUserByEmailIgnoreCase(username)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

    boolean result = authService.login(username, rawPassword);
    assertTrue(result);

    verify(userRepository).findUserByEmailIgnoreCase(username);
    verify(passwordEncoder).matches(rawPassword, encodedPassword);
}

@Test
void login_shouldReturnFalse_whenUserNotFound() {
    String username = "unknown@example.com";
    String rawPassword = "password";

    when(userRepository.findUserByEmailIgnoreCase(username)).thenReturn(Optional.empty());

    boolean result = authService.login(username, rawPassword);
    assertFalse(result);

    verify(userRepository).findUserByEmailIgnoreCase(username);
    verify(passwordEncoder, never()).matches(any(), any());
}

@Test
void login_shouldReturnFalse_whenPasswordDoesNotMatch() {
    String username = "user@example.com";
    String rawPassword = "wrongpass";
    String encodedPassword = "encoded";

    User user = new User();
    user.setPassword(encodedPassword);

    when(userRepository.findUserByEmailIgnoreCase(username)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

    boolean result = authService.login(username, rawPassword);
    assertFalse(result);

    verify(userRepository).findUserByEmailIgnoreCase(username);
    verify(passwordEncoder).matches(rawPassword, encodedPassword);
}

@Test
void register_shouldSaveUserAndReturnTrue() {
    Register register = new Register(
     "testuser",
     "password123",
     "FirstName",
     "LastName",
     "+7 (999) 123-45-67",
     null // или Role.USER, если нужно
    );

    when(passwordEncoder.encode(register.getPassword())).thenReturn("encodedPass");
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    boolean result = authService.register(register);
    assertTrue(result);

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCaptor.capture());

    User savedUser = userCaptor.getValue();
    assertEquals(register.getUsername(), savedUser.getEmail());
    assertEquals("encodedPass", savedUser.getPassword());
    assertEquals(register.getRole(), savedUser.getRole());

    verify(passwordEncoder).encode(register.getPassword());
}
}
