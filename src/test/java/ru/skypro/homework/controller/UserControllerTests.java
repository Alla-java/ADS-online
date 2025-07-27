package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests{

@Autowired
private MockMvc mockMvc;

@MockBean
private UserService userService;

@Autowired
private ObjectMapper objectMapper;

@Test
@WithMockUser(username = "user@example.com", roles = "USER")
void setPassword_ShouldReturnOk() throws Exception {
    NewPassword newPassword = new NewPassword();
    newPassword.setCurrentPassword("oldpass123");
    newPassword.setNewPassword("newpass456");

    mockMvc.perform(post("/users/set_password")
                     .with(csrf())
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(objectMapper.writeValueAsString(newPassword)))
     .andExpect(status().isOk());

    Mockito.verify(userService).updatePassword(newPassword);
}

@Test
@WithMockUser
void getCurrentUser_ShouldReturnUserDto() throws Exception {
    UserDto userDto = new UserDto(1, "anna@example.com", "Анна", "Иванова", "+7 (931) 123-45-67", Role.USER, "https://example.com/avatar.jpg");

    Mockito.when(userService.getUser()).thenReturn(userDto);

    mockMvc.perform(get("/users/me"))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.id").value(1))
     .andExpect(jsonPath("$.email").value("anna@example.com"))
     .andExpect(jsonPath("$.firstName").value("Анна"))
     .andExpect(jsonPath("$.lastName").value("Иванова"))
     .andExpect(jsonPath("$.phone").value("+7 (931) 123-45-67"))
     .andExpect(jsonPath("$.role").value("USER"))
     .andExpect(jsonPath("$.image").value("https://example.com/avatar.jpg"));
}

@Test
@WithMockUser(username = "user@example.com", roles = "USER")
void updateUserInfo_ShouldReturnOk() throws Exception {
    UpdateUser updateUser = new UpdateUser("Анна", "Петрова", "+7 (999) 111-22-33");

    mockMvc.perform(patch("/users/me")
                     .with(csrf())
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(objectMapper.writeValueAsString(updateUser)))
     .andExpect(status().isOk());

    Mockito.verify(userService).updateUser(updateUser);
}
}