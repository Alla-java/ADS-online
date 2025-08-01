package ru.skypro.homework.dto.ads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="Представление объявления")
public class AdDto{

@Schema(description="ID автора объявления", example="123")
private Integer author;


@Schema(description="Ссылка на картинку объявления", example="https://example.com/image.jpg")
private String image;


@Schema(description="ID объявления", example="456")
private Integer pk;


@Schema(description="Цена объявления", example="15000")
private Integer price;


@Schema(description="Заголовок объявления", example="Продам ноутбук")
private String title;
}