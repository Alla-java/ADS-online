package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

/**
 * Реализация сервиса UserDetailsService для Spring Security.
 * Обеспечивает загрузку данных пользователя при аутентификации.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загружает данные пользователя по email для аутентификации в Spring Security.
     *
     * @param email email пользователя, используемый в качестве имени пользователя (username)
     * @return UserDetails объект, содержащий данные пользователя, необходимые для аутентификации
     * @throws UsernameNotFoundException если пользователь с указанным email не найден в базе данных
     *
     * @implNote
     * 1. Ищет пользователя в базе данных по email (без учета регистра)
     * 2. Если пользователь не найден, генерирует исключение UsernameNotFoundException
     * 3. Создает объект UserDetails с:
     *    - username (используется email пользователя)
     *    - хэшированным паролем
     *    - ролью пользователя (преобразованную в строку)
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + email + " не найден"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
