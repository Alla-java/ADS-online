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

/**
 * Сервис для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    /**
     * Обновление пароля пользователя
     * @param passwordDto данные для обновления пароля
     */
    @Override
    public void updatePassword(NewPassword passwordDto) {
        User user = getCurrentUserFromSecurityContext();
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    /**
     * Получение информации о текущем пользователе
     * @return данные пользователя
     */
    @Override
    public UserDto getUser() {
        User user = getCurrentUserFromSecurityContext();
        return userMapper.userIntoUserDto(user);
    }

    /**
     * Обновление информации о пользователе
     * @param updateDto новые данные пользователя
     * @return обновленные данные пользователя
     */
    @Override
    public UpdateUser updateUser(UpdateUser updateDto) {
        User user = getCurrentUserFromSecurityContext();
        user = userMapper.updateUserIntoUser(user, updateDto);
        User savedUser = userRepository.save(user);
        return userMapper.userIntoUpdateUser(savedUser);
    }

    public User getCurrentUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
            return userRepository.findUserByEmailIgnoreCase(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));
    }
}
