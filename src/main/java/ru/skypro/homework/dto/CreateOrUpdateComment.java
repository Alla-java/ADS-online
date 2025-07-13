package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description="DTO для создания или обновления комментария")
public class CreateOrUpdateComment{

@Schema(description="Текст комментария", minLength=8, maxLength=64, example="Очень полезное объявление, спасибо!")
@NotBlank(message="Текст комментария не должен быть пустым")
@Size(min=8, max=64, message="Длина комментария должна быть от 8 до 64 символов")
private String text;
}