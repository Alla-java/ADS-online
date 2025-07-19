package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentsService;
import ru.skypro.homework.service.UserService;

import java.util.List;
@Service
public class CommentsServiceImpl implements CommentsService {

    @Override
    public CommentDto addComment(Integer adId, CreateOrUpdateComment req) {
        // TODO: Реализация будет позже
        throw new UnsupportedOperationException("Метод addComment() не реализован");
    }

    @Override
    public Comments getComments(Integer adId) {
        // TODO: Реализация будет позже
        throw new UnsupportedOperationException("Метод getComments() не реализован");
    }

    @Override
    public CommentDto editComment(Integer adId, Integer commentId, CreateOrUpdateComment req) {
        // TODO: Реализация будет позже
        throw new UnsupportedOperationException("Метод editComment() не реализован");
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        // TODO: Реализация будет позже
        throw new UnsupportedOperationException("Метод deleteComment() не реализован");
    }
}


//@Service
//public class CommentsServiceImpl implements CommentsService {
//    private final CommentRepository commentRepository;
//    private final AdRepository adRepository;
//    private final UserService userService;
//    private final CommentMapper commentMapper;
//
//    public CommentsServiceImpl(CommentRepository commentRepository,
//                               AdRepository adRepository,
//                               UserService userService,
//                               CommentMapper commentMapper) {
//        this.commentRepository = commentRepository;
//        this.adRepository = adRepository;
//        this.userService = userService;
//        this.commentMapper = commentMapper;
//    }
//
//    @Override
//    public CommentDto addComment(Integer adId, CreateOrUpdateComment req) {
//        Ad ad = adRepository.findById(adId.longValue())
//                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
//        UserDto author = userService.getUser();
//
//        Comment entity = new Comment();
//        entity.setAd(ad);
//        entity.setAuthor(author);
//        entity.setText(req.getText());
//        entity.setCreatedAt(System.currentTimeMillis());
//
//        Comment saved = commentRepository.save(entity);
//        return commentMapper.commentIntoCommentDto(saved);
//    }
//
//    @Override
//    public Comments getComments(Integer adId) {
//        List<Comment> entities = commentRepository.findByAdId(adId.longValue());
//        return commentMapper.listCommentIntoCommentsDto(entities);
//    }
//
//    @Override
//    public CommentDto editComment(Integer adId, Integer commentId, CreateOrUpdateComment req) {
//        Comment comment = commentRepository.findById(commentId.longValue())
//                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));
//
//        comment.setText(req.getText());
//        commentRepository.save(comment);
//
//        return commentMapper.commentIntoCommentDto(comment);
//    }
//
//    @Override
//    public void deleteComment(Integer adId, Integer commentId) {
//        commentRepository.deleteById(commentId.longValue());
//    }
//}
