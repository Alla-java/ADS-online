package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentsService;

/**
 * Контроллер для работы с комментариями к объявлениям.
 * Обеспечивает создание, чтение, обновление и удаление комментариев.
 */
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads/{adId}/comments")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    /**
     * Получает все комментарии для указанного объявления
     * @param adId идентификатор объявления
     * @return ResponseEntity с объектом Comments, содержащим список комментариев
     */
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Получение всех комментариев к объявлению")
    @GetMapping
    public ResponseEntity<Comments> getComments(@PathVariable Integer adId) {
        return ResponseEntity.ok(commentsService.getComments(adId));
    }

    /**
     * Добавляет новый комментарий к объявлению
     * @param adId идентификатор объявления
     * @param req данные нового комментария
     * @return ResponseEntity с созданным комментарием и кодом 201
     */
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Добавление комментария")
    @PostMapping
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer adId,
                                                 @RequestBody @Valid CreateOrUpdateComment req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentsService.addComment(adId, req));
    }

    /**
     * Редактирует существующий комментарий
     * @param adId идентификатор объявления (для валидации)
     * @param commentId идентификатор редактируемого комментария
     * @param req новые данные комментария
     * @return ResponseEntity с обновленным комментарием
     */
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfComment(#id)")
    @Operation(summary = "Редактирование комментария")
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto> editComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId,
            @RequestBody @Valid CreateOrUpdateComment req
    ) {
        return ResponseEntity.ok(commentsService.editComment(adId, commentId, req));
    }

    /**
     * Удаляет комментарий
     * @param adId идентификатор объявления (для валидации)
     * @param commentId идентификатор удаляемого комментария
     * @return ResponseEntity с кодом 204 (No Content)
     */
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfComment(#id)")
    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId
    ) {
        commentsService.deleteComment(adId, commentId);
        return ResponseEntity.noContent().build();
    }
}
