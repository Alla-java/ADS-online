package ru.skypro.homework.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
