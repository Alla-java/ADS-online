package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.skypro.homework.model.User;

import java.util.Optional;

/**
 * Репозиторий для работы с пользователями (User) в базе данных.
 * Обеспечивает доступ к данным пользователей и специализированные запросы.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
        /**
         * Находит пользователя по email (без учета регистра)
         * @param email email пользователя для поиска
         * @return Optional с найденным пользователем или пустой, если не найден
         */
        Optional<User> findUserByEmailIgnoreCase(String email);

    }


