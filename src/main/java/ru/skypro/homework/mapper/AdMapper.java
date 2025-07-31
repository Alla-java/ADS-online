package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAd;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

/**
 * Маппер для преобразования между сущностью Ad и DTO
 */
@Component
public class AdMapper {

@Value("${path.images}")
private  String images;

/**
 * Преобразование Ad в AdDto
 * @param ad сущность объявления
 * @return DTO объявления
 */
public AdDto adIntoAdDto(Ad ad) {
    return AdDto.builder()
            .pk(ad.getId())
            .title(ad.getTitle())
            .price(ad.getPrice())
            .image(ad.getImage() != null ? images + ad.getImage().getId() : null)
            .author(ad.getAuthor().getId())
            .build();
}

/**
 * Преобразование Ad в ExtendedAd
 * @param ad сущность объявления
 * @return расширенное DTO объявления
 */
public ExtendedAd toExtendedDto(Ad ad) {
    return ExtendedAd.builder()
            .pk(ad.getId())
            .title(ad.getTitle())
            .description(ad.getDescription())
            .price(ad.getPrice())
            .image(ad.getImage() != null ? images + ad.getImage().getId() : null)
            .email(ad.getAuthor().getEmail())
            .phone(ad.getAuthor().getPhone())
            .authorFirstName(ad.getAuthor().getFirstName())
            .authorLastName(ad.getAuthor().getLastName())
            .build();
}

/**
 * Создание сущности Ad из DTO
 * @param dto DTO для создания объявления
 * @param author автор объявления
 * @return сущность объявления
 */
public Ad toEntity(CreateOrUpdateAd dto, User author) {
    return Ad.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .author(author)
            .build();
}

/**
 * Обновление сущности Ad из DTO
 * @param ad сущность объявления для обновления
 * @param dto DTO с новыми данными
 */
public void updateEntity(Ad ad, CreateOrUpdateAd dto) {
    ad.setTitle(dto.getTitle());
    ad.setDescription(dto.getDescription());
    ad.setPrice(dto.getPrice());
}
}
