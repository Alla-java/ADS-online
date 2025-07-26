package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name="Объявления")

public class AdController{

private final AdService adService;

@PreAuthorize("hasRole('USER')")
@Operation(summary = "Получение всех объявлений")
@GetMapping
public Ads getAllAds(){
    return adService.getAllAds();
}

@PreAuthorize("hasRole('USER')")
@Operation(summary = "Добавление объявления")
@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public AdDto addAd(
 @RequestParam String title,
 @RequestParam Integer price,
 @RequestParam String description,
 @RequestPart MultipartFile image) throws Exception {

    CreateOrUpdateAd dto = CreateOrUpdateAd.builder()
                            .title(title)
                            .price(price)
                            .description(description)
                            .build();

    return adService.addAd(dto, image);
}

@PreAuthorize("hasRole('USER')")
@Operation(summary = "Получение информации об объявлении")
@GetMapping("/{id}")
public ExtendedAdDto getAd(@PathVariable Integer id){
    return adService.getAd(id);
}

@PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfAd(#id)")
@Operation(summary = "Удаление объявления")
@DeleteMapping("/{id}")
public void deleteAd(@PathVariable Integer id){
    adService.deleteAd(id);
}

@PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfAd(#id)")
@Operation(summary = "Обновление информации об объявлении")
@PatchMapping("/{id}")
public AdDto updateAd(@PathVariable Integer id,@RequestBody @Valid CreateOrUpdateAd dto){
    return adService.updateAd(id,dto);
}

@PreAuthorize("hasRole('USER')")
@Operation(summary = "Получение объявлений авторизованного пользователя")
@GetMapping("/me")
public Ads getMyAds(){
    return adService.getMyAds();
}

}
