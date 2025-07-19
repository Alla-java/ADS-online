package ru.skypro.homework.service;

import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;

public interface CommentsService {
    CommentDto addComment(Integer adId, CreateOrUpdateComment req);
    Comments getComments(Integer adId);
    CommentDto editComment(Integer adId, Integer commentId, CreateOrUpdateComment req);
    void deleteComment(Integer adId, Integer commentId);
}
