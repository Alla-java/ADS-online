package ru.skypro.homework.service;

import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;

public interface CommentsService {
    Comment addComment(Integer adId, CreateOrUpdateComment req);
    Comments getComments(Integer adId);
    Comment editComment(Integer adId, Integer commentId, CreateOrUpdateComment req);
    void deleteComment(Integer adId, Integer commentId);
}
