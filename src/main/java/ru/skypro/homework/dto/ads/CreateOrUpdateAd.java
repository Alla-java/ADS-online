package ru.skypro.homework.dto.ads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateAd {

@Size(min = 4, max = 32, message = "Заголовок должен содержать от 4 до 32 символов")
private String title;

@Min(value = 0, message = "Цена должна быть не меньше 0")
@Max(value = 10000000, message = "Цена должна быть не больше 10000000")
private Integer price;

@Size(min = 8, max = 64, message = "Описание объявления должно содержать от 8 до 64 символов")
private String description;

}
