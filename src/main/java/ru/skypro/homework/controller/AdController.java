package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAd;
import ru.skypro.homework.service.AdService;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name="Объявления")

public class AdController{

private final AdService adService;

@Operation(summary = "Получение всех объявлений")
@GetMapping
public Ads getAllAds(){
    return adService.getAllAds();
}

@Operation(summary = "Добавление объявления")
@PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
public Ad addAd(@RequestPart("properties") @Valid CreateOrUpdateAd properties,@RequestPart("image") MultipartFile image){
    return adService.addAd(properties,image);
}

@Operation(summary = "Получение информации об объявлении")
@GetMapping("/{id}")
public ExtendedAd getAd(@PathVariable Integer id){
    return adService.getAd(id);
}

@Operation(summary = "Удаление объявления")
@DeleteMapping("/{id}")
public void deleteAd(@PathVariable Integer id){
    adService.deleteAd(id);
}

@Operation(summary = "Обновление информации об объявлении")
@PatchMapping("/{id}")
public Ad updateAd(@PathVariable Integer id,@RequestBody @Valid CreateOrUpdateAd dto){
    return adService.updateAd(id,dto);
}

@Operation(summary = "Получение объявлений авторизованного пользователя")
@GetMapping("/me")
public Ads getMyAds(){
    return adService.getMyAds();
}

}
