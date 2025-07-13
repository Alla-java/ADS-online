package ru.skypro.homework.dto.ads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description="Расширенное описание объявления")
public class ExtendedAd{

@Schema(description="ID объявления", example="101")
private Integer pk;

@Schema(description="Имя автора объявления", example="Иван")
private String authorFirstName;

@Schema(description="Фамилия автора объявления", example="Иванов")
private String authorLastName;

@Schema(description="Описание объявления", example="Состояние отличное, почти не использовался.")
private String description;

@Schema(description="Логин (email) автора объявления", example="ivan@example.com")
private String email;

@Schema(description="Ссылка на картинку объявления", example="https://example.com/image.jpg")
private String image;

@Schema(description="Телефон автора объявления", example="+7 900 123-45-67")
private String phone;

@Schema(description="Цена объявления", example="25000")
private Integer price;

@Schema(description="Заголовок объявления", example="Продаю гитару")
private String title;
}

