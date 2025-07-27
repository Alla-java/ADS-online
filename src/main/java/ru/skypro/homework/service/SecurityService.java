package ru.skypro.homework.service;

import org.springframework.stereotype.Component;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;

@Component("securityService")
public class SecurityService {
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    public SecurityService(AdRepository adRepository, CommentRepository commentRepository, UserService userService) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    // Проверка на то что объявление пренадлежит пользователю
    public boolean isOwnerOfAd(Integer adId) {
        var currentUser = userService.getUser();
        return adRepository.findById(adId)
                .map(ad -> ad.getAuthor().getId().equals(currentUser.getId()))
                .orElse(false);
    }
    // Проверка на то что комментарий пренадлежит пользователю
    public boolean isOwnerOfComment(Integer commentId) {
    var currentUser = userService.getUser();
    return commentRepository.findById(commentId)
        .map(comment -> comment.getAuthor().getId().equals(currentUser.getId()))
        .orElse(false);
  }
}
