package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.model.Comment;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CommentMapper {

@Value("${path.images}")
private  String images;

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
