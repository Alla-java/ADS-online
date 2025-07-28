package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

@Component
public class AdMapper {

@Value("${path.images}")
private  String images;

public AdDto adIntoAdDto(Ad ad) {
    return AdDto.builder()
            .pk(ad.getId())
            .title(ad.getTitle())
            .price(ad.getPrice())
            .image(ad.getImage() != null ? images + ad.getImage().getId() : null)
            .author(ad.getAuthor().getId())
            .build();
}

public ExtendedAdDto toExtendedDto(Ad ad) {
    return ExtendedAdDto.builder()
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

public Ad toEntity(CreateOrUpdateAd dto, User author) {
    return Ad.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .author(author)
            .build();
}

public void updateEntity(Ad ad, CreateOrUpdateAd dto) {
    ad.setTitle(dto.getTitle());
    ad.setDescription(dto.getDescription());
    ad.setPrice(dto.getPrice());
}
}
