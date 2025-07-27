package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentsService;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentsControllerTests{
@Autowired
private MockMvc mockMvc;

@MockBean
private CommentsService commentsService;

@Autowired
private ObjectMapper objectMapper;

private final int adId = 1;
private final int commentId = 123;

@Test
@WithMockUser(roles = "USER")
void getComments_ReturnsComments() throws Exception {
    CommentDto comment = new CommentDto();
    comment.setPk(commentId);
    comment.setAuthor(321);
    comment.setAuthorFirstName("Анна");
    comment.setAuthorImage("https://example.com/avatar.jpg");
    comment.setCreatedAt(1657891234);
    comment.setText("Отличное предложение!");

    Comments comments = new Comments();
    comments.setCount(1);
    comments.setResults(List.of(comment));

    Mockito.when(commentsService.getComments(adId)).thenReturn(comments);

    mockMvc.perform(get("/ads/{adId}/comments", adId))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.count").value(1))
     .andExpect(jsonPath("$.results[0].pk").value(commentId))
     .andExpect(jsonPath("$.results[0].text").value("Отличное предложение!"));
}

@Test
@WithMockUser(roles = "USER")
void addComment_ShouldReturnCreatedComment() throws Exception {
    CreateOrUpdateComment req = new CreateOrUpdateComment();
    req.setText("Очень полезное объявление, спасибо!");

    CommentDto returnedComment = new CommentDto();
    returnedComment.setPk(commentId);
    returnedComment.setAuthor(321);
    returnedComment.setAuthorFirstName("Анна");
    returnedComment.setAuthorImage("https://example.com/avatar.jpg");
    returnedComment.setCreatedAt(1657891234);
    returnedComment.setText(req.getText());

    Mockito.when(commentsService.addComment(eq(adId), any(CreateOrUpdateComment.class)))
     .thenReturn(returnedComment);

    mockMvc.perform(post("/ads/{adId}/comments", adId)
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(objectMapper.writeValueAsString(req))
                     .with(csrf())
     )
     .andExpect(status().isCreated())
     .andExpect(jsonPath("$.pk").value(commentId))
     .andExpect(jsonPath("$.text").value(req.getText()))
     .andExpect(jsonPath("$.author").value(321))
     .andExpect(jsonPath("$.authorFirstName").value("Анна"));
}

@Test
@WithMockUser(roles = "ADMIN")
void editComment_ShouldReturnUpdatedComment() throws Exception {
    CreateOrUpdateComment req = new CreateOrUpdateComment();
    req.setText("Обновленный текст комментария");

    CommentDto updatedComment = new CommentDto();
    updatedComment.setPk(commentId);
    updatedComment.setAuthor(321);
    updatedComment.setAuthorFirstName("Анна");
    updatedComment.setAuthorImage("https://example.com/avatar.jpg");
    updatedComment.setCreatedAt(1657891234);
    updatedComment.setText(req.getText());

    Mockito.when(commentsService.editComment(eq(adId), eq(commentId), any(CreateOrUpdateComment.class)))
     .thenReturn(updatedComment);

    mockMvc.perform(put("/ads/{adId}/comments/{commentId}", adId, commentId)
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(objectMapper.writeValueAsString(req))
                     .with(csrf())
     )
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.pk").value(commentId))
     .andExpect(jsonPath("$.text").value(req.getText()));
}

@Test
@WithMockUser(roles = "ADMIN")
void deleteComment_ShouldReturnNoContent() throws Exception {

    Mockito.doNothing().when(commentsService).deleteComment(adId, commentId);

    mockMvc.perform(delete("/ads/{adId}/comments/{commentId}", adId, commentId)
                     .with(csrf())
     )
     .andExpect(status().isNoContent());

    Mockito.verify(commentsService).deleteComment(adId, commentId);
}
}