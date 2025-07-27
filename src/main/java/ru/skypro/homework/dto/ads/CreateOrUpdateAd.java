package ru.skypro.homework.dto.ads;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="DTO для создания или обновления объявления")
public class CreateOrUpdateAd{

@Schema(description="заголовок объявления", minLength=4, maxLength=32, example="Продам велосипед")
@NotBlank(message="Заголовок не должен быть пустым")
@Size(min=4, max=32, message="Длина заголовка должна быть от 4 до 32 символов")
private String title;

@Schema(description="цена объявления", minimum="0", maximum="10000000", example="15000")
@NotNull(message="Цена не должна быть null")
@Min(value=0, message="Цена не может быть меньше 0")
@Max(value=10000000, message="Цена не может быть больше 10 000 000")
private Integer price;

@Schema(description="описание объявления", minLength=8, maxLength=64, example="Практически новый велосипед, ездили пару раз.")
@NotBlank(message="Описание не должно быть пустым")
@Size(min=8, max=64, message="Длина описания должна быть от 8 до 64 символов")
private String description;
}