package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;

import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.CommentsServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        Integer adId = 1;
        Ad ad = new Ad();
        User user = new User();
        user.setId(100);

        CreateOrUpdateComment req = new CreateOrUpdateComment();
        req.setText("Test comment");

        Comment savedEntity = new Comment();
        savedEntity.setId(10);
        savedEntity.setAuthor(user);
        savedEntity.setAd(ad);
        savedEntity.setText("Test comment");
        savedEntity.setCreatedAt((int) System.currentTimeMillis());

        CommentDto dto = new CommentDto();
        dto.setPk(10);
        dto.setText("Test comment");

        when(adRepository.findById(adId)).thenReturn(Optional.of(ad));
        when(userService.getCurrentUser()).thenReturn(user);
        when(commentRepository.save(any(Comment.class))).thenReturn(savedEntity);
        when(commentMapper.commentIntoCommentDto(savedEntity)).thenReturn(dto);

        CommentDto result = commentsService.addComment(adId.intValue(), req);

        assertEquals(dto.getPk(), result.getPk());
        assertEquals(dto.getText(), result.getText());
    }

    @Test
    void testGetComments() {
        Integer adId = 1;
        List<Comment> commentEntities = List.of(new Comment());
        Comments expectedDto = new Comments();
        expectedDto.setCount(1);
        expectedDto.setResults(List.of(new CommentDto()));

        when(commentRepository.findByAdId(adId)).thenReturn(commentEntities);
        when(commentMapper.listCommentIntoCommentsDto(commentEntities)).thenReturn(expectedDto);

        Comments result = commentsService.getComments(adId.intValue());

        assertEquals(1, result.getCount());
    }

    @Test
    void testEditComment() {
        Integer commentId = 2;
        CreateOrUpdateComment req = new CreateOrUpdateComment();
        req.setText("Updated comment");

        Comment entity = new Comment();
        entity.setId(commentId);
        entity.setText("Old comment");

        CommentDto expectedDto = new CommentDto();
        expectedDto.setPk(commentId.intValue());
        expectedDto.setText("Updated comment");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(entity));
        when(commentRepository.save(entity)).thenReturn(entity);
        when(commentMapper.commentIntoCommentDto(entity)).thenReturn(expectedDto);

        CommentDto result = commentsService.editComment(1, commentId.intValue(), req);

        assertEquals("Updated comment", result.getText());
    }

    @Test
    void testDeleteComment() {
        Integer commentId = 5;

        doNothing().when(commentRepository).deleteById(commentId);

        // если не кидает исключения — тест прошёл
        assertDoesNotThrow(() ->
                commentsService.deleteComment(1, commentId.intValue())
        );

        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
