package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentsService;
import ru.skypro.homework.service.UserService;

import java.util.List;

public class CommentsServiceImpl implements CommentsService {
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;

    public CommentsServiceImpl(CommentRepository commentRepository,
                               AdRepository adRepository,
                               UserService userService,
                               CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.userService = userService;
        this.commentMapper = commentMapper;
    }

    @Override
    public Comment addComment(Integer adId, CreateOrUpdateComment req) {
        Ad ad = adRepository.findById(adId.longValue())
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
        User author = userService.getCurrentUser();

        CommentEntity entity = new CommentEntity();
        entity.setAd(ad);
        entity.setAuthor(author);
        entity.setText(req.getText());
        entity.setCreatedAt(System.currentTimeMillis());

        CommentEntity saved = commentRepository.save(entity);
        return commentMapper.commentIntoCommentDto(saved);
    }

    @Override
    public Comments getComments(Integer adId) {
        List<CommentEntity> entities = commentRepository.findByAdId(adId.longValue());
        return commentMapper.listCommentIntoCommentsDto(entities);
    }

    @Override
    public Comment editComment(Integer adId, Integer commentId, CreateOrUpdateComment req) {
        CommentEntity comment = commentRepository.findById(commentId.longValue())
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        comment.setText(req.getText());
        commentRepository.save(comment);

        return commentMapper.commentIntoCommentDto(comment);
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        commentRepository.deleteById(commentId.longValue());
    }
}
