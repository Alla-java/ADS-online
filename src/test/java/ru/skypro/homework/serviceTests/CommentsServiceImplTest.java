package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.CommentsServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentsServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AdRepository adRepository;

    @Mock
    private UserService userService;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentsServiceImpl commentsService;

    @Test
    void testAddComment() {
        Long adId = 1L;
        Ad ad = new Ad();
        User user = new User();
        user.setId(100L);

        CreateOrUpdateComment req = new CreateOrUpdateComment();
        req.setText("Test comment");

        CommentEntity savedEntity = new CommentEntity();
        savedEntity.setId(10L);
        savedEntity.setAuthor(user);
        savedEntity.setAd(ad);
        savedEntity.setText("Test comment");
        savedEntity.setCreatedAt(System.currentTimeMillis());

        Comment dto = new Comment();
        dto.setPk(10);
        dto.setText("Test comment");

        when(adRepository.findById(adId)).thenReturn(Optional.of(ad));
        when(userService.getCurrentUser()).thenReturn(user);
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(savedEntity);
        when(commentMapper.commentIntoCommentDto(savedEntity)).thenReturn(dto);

        Comment result = commentsService.addComment(adId.intValue(), req);

        assertEquals(dto.getPk(), result.getPk());
        assertEquals(dto.getText(), result.getText());
    }

    @Test
    void testGetComments() {
        Long adId = 1L;
        List<CommentEntity> commentEntities = List.of(new CommentEntity());
        Comments expectedDto = new Comments();
        expectedDto.setCount(1);
        expectedDto.setResults(List.of(new Comment()));

        when(commentRepository.findByAdId(adId)).thenReturn(commentEntities);
        when(commentMapper.listCommentIntoCommentsDto(commentEntities)).thenReturn(expectedDto);

        Comments result = commentsService.getComments(adId.intValue());

        assertEquals(1, result.getCount());
    }

    @Test
    void testEditComment() {
        Long commentId = 2L;
        CreateOrUpdateComment req = new CreateOrUpdateComment();
        req.setText("Updated comment");

        CommentEntity entity = new CommentEntity();
        entity.setId(commentId);
        entity.setText("Old comment");

        Comment expectedDto = new Comment();
        expectedDto.setPk(commentId.intValue());
        expectedDto.setText("Updated comment");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(entity));
        when(commentRepository.save(entity)).thenReturn(entity);
        when(commentMapper.commentIntoCommentDto(entity)).thenReturn(expectedDto);

        Comment result = commentsService.editComment(1, commentId.intValue(), req);

        assertEquals("Updated comment", result.getText());
    }

    @Test
    void testDeleteComment() {
        Long commentId = 5L;

        doNothing().when(commentRepository).deleteById(commentId);

        // если не кидает исключения — тест прошёл
        assertDoesNotThrow(() ->
                commentsService.deleteComment(1, commentId.intValue())
        );

        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
