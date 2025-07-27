package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.CommentsServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceImplTests{

@Mock
private CommentRepository commentRepository;
@Mock
private AdRepository adRepository;
@Mock
private UserService userService;
@Mock
private CommentMapper commentMapper;
@Mock
private UserMapper userMapper;

@InjectMocks
private CommentsServiceImpl commentsService;

private Ad testAd;
private UserDto testUserDto;
private User testUser;
private Comment testComment;
private CreateOrUpdateComment createOrUpdateComment;

@BeforeEach
void setup(){
    testAd=new Ad();
    testAd.setId(1);

    testUserDto=new UserDto();
    testUserDto.setId(1);
    testUserDto.setEmail("user@example.com");

    testUser=new User();
    testUser.setId(1);
    testUser.setEmail("user@example.com");

    testComment=new Comment();
    testComment.setId(1);
    testComment.setAd(testAd);
    testComment.setAuthor(testUser);
    testComment.setText("Initial comment");

    createOrUpdateComment=new CreateOrUpdateComment();
    createOrUpdateComment.setText("Updated comment");
}

@Test
void addComment_shouldSaveAndReturnDto(){
    when(adRepository.findById(1)).thenReturn(Optional.of(testAd));
    when(userService.getUser()).thenReturn(testUserDto);
    when(userMapper.userDtoIntoUser(testUserDto)).thenReturn(testUser);
    when(commentRepository.save(any(Comment.class))).thenAnswer(i->i.getArgument(0));
    when(commentMapper.commentIntoCommentDto(any(Comment.class))).thenReturn(new CommentDto());

    CommentDto result=commentsService.addComment(1,createOrUpdateComment);

    assertNotNull(result);
    verify(commentRepository).save(any(Comment.class));
}

@Test
void addComment_shouldThrowWhenAdNotFound(){
    when(adRepository.findById(1)).thenReturn(Optional.empty());

    RuntimeException exception=assertThrows(RuntimeException.class,()->commentsService.addComment(1,createOrUpdateComment));
    assertEquals("Объявление не найдено",exception.getMessage());
}

@Test
void getComments_shouldReturnCommentsDto(){
    List<Comment> comments=List.of(testComment);
    when(commentRepository.findByAdId(1)).thenReturn(comments);
    Comments commentsDto=new Comments();
    when(commentMapper.listCommentIntoCommentsDto(comments)).thenReturn(commentsDto);

    Comments result=commentsService.getComments(1);

    assertEquals(commentsDto,result);
    verify(commentRepository).findByAdId(1);
}

@Test
void editComment_shouldUpdateAndReturnDto(){
    when(commentRepository.findById(1)).thenReturn(Optional.of(testComment));
    when(commentRepository.save(testComment)).thenReturn(testComment);
    when(commentMapper.commentIntoCommentDto(testComment)).thenReturn(new CommentDto());

    CommentDto result=commentsService.editComment(1,1,createOrUpdateComment);

    assertNotNull(result);
    assertEquals("Updated comment",testComment.getText());
    verify(commentRepository).save(testComment);
}

@Test
void editComment_shouldThrowWhenCommentNotFound(){
    when(commentRepository.findById(1)).thenReturn(Optional.empty());

    RuntimeException exception=assertThrows(RuntimeException.class,()->commentsService.editComment(1,1,createOrUpdateComment));
    assertEquals("Комментарий не найден",exception.getMessage());
}

@Test
void deleteComment_shouldCallRepositoryDelete(){
    commentsService.deleteComment(1,1);
    verify(commentRepository).deleteById(1);
}
}