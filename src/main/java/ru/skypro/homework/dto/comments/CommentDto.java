package ru.skypro.homework.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Комментарий к объявлению")
public class CommentDto {

@Schema(description="ID автора комментария", example="321")
private Integer author;

@Schema(description="Ссылка на аватар автора комментария", example="https://example.com/avatar.jpg")
private String authorImage;

@Schema(description="Имя создателя комментария", example="Анна")
private String authorFirstName;

@Schema(description="Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970", example="1657891234567")
private Integer createdAt;

@Schema(description="ID комментария", example="987")
private Integer pk;

@Schema(description="Текст комментария", example="Отличное предложение, рекомендую!")
private String text;
}