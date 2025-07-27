package ru.skypro.homework.dto.ads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description="Список объявлений и их количество")
public class Ads{

@Schema(description="Общее количество объявлений", example="25")
private Integer count;

@Schema(description="Список объявлений")
private List<AdDto> results;
}

