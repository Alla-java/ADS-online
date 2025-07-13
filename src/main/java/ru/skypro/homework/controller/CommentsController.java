package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentsService;

import java.util.ArrayList;

@RestController
@RequestMapping("/comments")
public class CommentsController implements CommentsService {

    @GetMapping("/{adId}")
    public Comments getComments(@PathVariable Integer adId) {
        // Возвращаем заглушку
        Comments comments = new Comments();
        comments.setCount(0);
        comments.setResults(new ArrayList<>());
        return comments;
    }

    @PostMapping("/{adId}")
    public Comment addComment(@PathVariable Integer adId,
                              @RequestBody CreateOrUpdateComment req) {
        // Возвращаем заглушку
        Comment comment = new Comment();
        comment.setPk(1);
        comment.setText(req.getText());
        comment.setAuthor(123); // временно
        comment.setAuthorFirstName("Demo");
        comment.setAuthorImage("demo.png");
        comment.setCreatedAt(System.currentTimeMillis());
        return comment;
    }
}
