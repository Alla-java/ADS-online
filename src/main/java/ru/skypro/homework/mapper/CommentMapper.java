package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.model.Comment;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CommentMapper {
    public CommentDto commentIntoCommentDto (Comment entity) {
        CommentDto dto = new CommentDto();
        dto.setPk(entity.getId().intValue());
        dto.setText(entity.getText());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setAuthor(entity.getAuthor().getId().intValue());
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setAuthorImage(entity.getAuthor().getImage() != null
                ? "/images/" + entity.getAuthor().getImage().getId()
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
