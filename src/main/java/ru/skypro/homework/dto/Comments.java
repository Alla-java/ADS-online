package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Schema(description="DTO, представляющий список комментариев и их общее количество")
public class Comments{

@Schema(description="Общее количество комментариев", example="10")
private Integer count;

@Schema(description="Список комментариев")
private List<Comment> results;
}