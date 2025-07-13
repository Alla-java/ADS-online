package ru.skypro.homework.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description="DTO для создания или обновления комментария")
public class CreateOrUpdateComment{

@Schema(description="Текст комментария", minLength=8, maxLength=64, example="Очень полезное объявление, спасибо!")
@NotBlank(message="Текст комментария не должен быть пустым")
@Size(min=8, max=64, message="Длина комментария должна быть от 8 до 64 символов")
private String text;
}