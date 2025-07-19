package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.model.CommentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public Comment commentIntoCommentDto (CommentEntity entity) {
        Comment dto = new Comment();
        dto.setPk(entity.getId().intValue());
        dto.setText(entity.getText());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setAuthor(entity.getAuthor().getId().intValue());
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setAuthorImage(entity.getAuthor().getImageUrl());
        return dto;
    }

    public Comments listCommentIntoCommentsDto(List<CommentEntity> entities) {
        List<Comment> commentDtos = entities.stream()
                .map(this::commentIntoCommentDto) // каждый CommentEntity → DTO
                .collect(Collectors.toList());

        Comments comments = new Comments();
        comments.setCount(commentDtos.size());
        comments.setResults(commentDtos);
        return comments;
    }
}
