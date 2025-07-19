package ru.skypro.homework.dto.ads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Data
@Builder
@Schema(description="Представление объявления")
public class AdDto{

@Schema(description="ID автора объявления", example="123")
private Integer author;


@Schema(description="Ссылка на картинку объявления", example="https://example.com/image.jpg")
private Image image;


@Schema(description="ID объявления", example="456")
private Long pk;


@Schema(description="Цена объявления", example="15000")
private Integer price;


@Schema(description="Заголовок объявления", example="Продам ноутбук")
private String title;
}