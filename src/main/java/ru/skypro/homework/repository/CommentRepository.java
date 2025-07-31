package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import java.util.List;

/**
 * Репозиторий для работы с комментариями (Comment) в базе данных.
 * Обеспечивает доступ к данным комментариев и специализированные запросы.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * Находит все комментарии по идентификатору объявления
     * @param adId идентификатор объявления
     * @return список комментариев к указанному объявлению
     */
    List<Comment> findByAdId(Integer adId);
}
