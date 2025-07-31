package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

/**
 * Сервис для аутентификации и регистрации пользователей
 */
@Service
public class AuthServiceImpl implements AuthService {
;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository) {
        this.encoder = passwordEncoder;
        this.userRepository=userRepository;
    }

    /**
     * Аутентификация пользователя
     * @param username имя пользователя (email)
     * @param password пароль
     * @return true, если аутентификация успешна, иначе false
     */
@Override
public boolean login(String username, String password) {
    return userRepository.findUserByEmailIgnoreCase(username)
            .map(user -> encoder.matches(password, user.getPassword()))
            .orElse(false);
}

    /**
     * Регистрация нового пользователя
     * @param register данные для регистрации
     * @return true, если регистрация успешна, иначе false
     */
@Override
public boolean register(Register register) {
    User user = new User();

    user.setEmail(register.getUsername());
    user.setPassword(encoder.encode(register.getPassword()));
    user.setRole(register.getRole());
    userRepository.save(user);
    return true;
}

}
