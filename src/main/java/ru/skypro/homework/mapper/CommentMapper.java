package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования между сущностью Comment и DTO
 */
@Component
public class CommentMapper {

@Value("${path.images}")
private  String images;

    /**
     * Преобразование Comment в CommentDto
     * @param entity сущность комментария
     * @return DTO комментария
     */
    public CommentDto commentIntoCommentDto (Comment entity) {
        CommentDto dto = new CommentDto()

        .setPk(entity.getId().intValue())
        .setText(entity.getText())
        .setCreatedAt(entity.getCreatedAt())
        .setAuthor(entity.getAuthor().getId().intValue())
        .setAuthorFirstName(entity.getAuthor().getFirstName())
        .setAuthorImage(entity.getAuthor().getImage() != null
                ? images + entity.getAuthor().getImage().getId()
                : null);
        return dto;
    }

    /**
     * Преобразование списка комментариев в DTO с информацией о количестве
     * @param entities список сущностей комментариев
     * @return DTO со списком комментариев и их количеством
     */
    public Comments listCommentIntoCommentsDto(List<Comment> entities) {
        List<CommentDto> commentDtoDtos = entities.stream()
                .map(this::commentIntoCommentDto) // каждый CommentEntity → DTO
                .collect(Collectors.toList());

        Comments comments = new Comments();
        comments.setCount(commentDtoDtos.size());
        comments.setResults(commentDtoDtos);
        return comments;
    }
}
