package ru.skypro.homework.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentsService;

@RestController
@RequestMapping("/ads/{adId}/comments")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping
    public ResponseEntity<Comments> getComments(@PathVariable Integer adId) {
        return ResponseEntity.ok(commentsService.getComments(adId));
    }

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer adId,
                                                 @RequestBody @Valid CreateOrUpdateComment req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentsService.addComment(adId, req));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> editComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId,
            @RequestBody @Valid CreateOrUpdateComment req
    ) {
        return ResponseEntity.ok(commentsService.editComment(adId, commentId, req));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId
    ) {
        commentsService.deleteComment(adId, commentId);
        return ResponseEntity.noContent().build();
    }
}
