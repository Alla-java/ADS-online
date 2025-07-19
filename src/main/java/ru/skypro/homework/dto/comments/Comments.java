package ru.skypro.homework.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description="DTO, представляющий список комментариев и их общее количество")
public class Comments{

@Schema(description="Общее количество комментариев", example="10")
private Integer count;

@Schema(description="Список комментариев")
private List<CommentDto> results;
}