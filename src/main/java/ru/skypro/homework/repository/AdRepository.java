package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.skypro.homework.model.Ad;

import java.util.List;

/**
 * Репозиторий для работы с объявлениями в базе данных.
 * Расширяет JpaRepository для стандартных CRUD-операций.
 */
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer>{

/**
 * Находит все объявления по ID автора
 * @param authorId ID автора объявлений
 * @return список объявлений указанного автора
 */
List<Ad> findAllByAuthorId(Integer authorId);
}