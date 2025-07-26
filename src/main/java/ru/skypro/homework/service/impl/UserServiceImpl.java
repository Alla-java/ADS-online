package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

private final UserRepository userRepository;
private final UserMapper userMapper;
private final PasswordEncoder passwordEncoder;
private final ImageService imageService;

@Override
public void updatePassword(NewPassword passwordDto) {
    User user = getCurrentUserFromSecurityContext();
    user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
    userRepository.save(user);
}

@Override
public UserDto getUser() {
    User user = getCurrentUserFromSecurityContext();
    return userMapper.userIntoUserDto(user);
}

@Override
public void updateUser(UpdateUser updateDto) {
    User user = getCurrentUserFromSecurityContext();
    userMapper.updateUserIntoUser(user, updateDto);
    userRepository.save(user);
}

// Вспомогательный метод для получения текущего авторизованного пользователя
private User getCurrentUserFromSecurityContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    return userRepository.findUserByEmailIgnoreCase(email)
            .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));
}
}