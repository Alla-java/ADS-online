package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.service.AuthService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests{
@Autowired
private MockMvc mockMvc;

@MockBean
private AuthService authService;

@Autowired
private ObjectMapper objectMapper;

@Test
void login_ShouldReturnOk_WhenCredentialsAreCorrect() throws Exception{
    Login login=new Login("user@example.com","password123");

    Mockito.when(authService.login(login.getUsername(),login.getPassword())).thenReturn(true);

    mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(login))).andExpect(status().isOk());
}

@Test
void login_ShouldReturnUnauthorized_WhenCredentialsInvalid() throws Exception{
    Login login=new Login("user@example.com","wrongpass");

    Mockito.when(authService.login(login.getUsername(),login.getPassword())).thenReturn(false);

    mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(login))).andExpect(status().isUnauthorized());
}

@Test
void register_ShouldReturnCreated_WhenRegistrationSuccessful() throws Exception{
    Register register=new Register("newuser@example.com","StrongPass123","Ivan","Petrov","+7 (999) 123-45-67",Role.USER);

    Mockito.when(authService.register(register)).thenReturn(true);

    mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(register))).andExpect(status().isCreated());
}

@Test
void register_ShouldReturnBadRequest_WhenRegistrationFails() throws Exception{
    Register register=new Register("existing@example.com","weakpass","Anna","Ivanova","+7 (911) 000-00-00",Role.USER);

    Mockito.when(authService.register(register)).thenReturn(false);

    mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(register))).andExpect(status().isBadRequest());
}
}

