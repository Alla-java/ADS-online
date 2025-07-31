package ru.skypro.homework.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с изображениями (Image) в базе данных.
 * Обеспечивает стандартные CRUD-операции для хранения изображений.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    // Наследует все стандартные методы JpaRepository:
    // save(), findById(), findAll(), deleteById() и др.
}
