package ru.skypro.homework.service;

import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;

public interface CommentsService {
    Comments getComments(Integer adId);
    Comment addComment(Integer adId, CreateOrUpdateComment req);
}
