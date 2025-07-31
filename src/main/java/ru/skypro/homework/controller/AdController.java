package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAd;
import ru.skypro.homework.service.AdService;

import static ru.skypro.homework.model.Roles.USER;

/**
 * Контроллер для работы с объявлениями.
 * Обеспечивает CRUD-операции с объявлениями и управление изображениями объявлений.
 */
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name = "Объявления")

public class AdController {

private final AdService adService;

/**
 * Получает список всех объявлений
 * @return объект Ads с количеством и списком объявлений
 */
@PreAuthorize("hasRole('USER')")
@Operation(summary = "Получение всех объявлений")
@GetMapping
public Ads getAllAds(){
    return adService.getAllAds();
}

/**
 * Создает новое объявление
 * @param properties данные объявления
 * @param image изображение объявления
 * @return созданное объявление
 * @throws Exception при ошибке обработки изображения
 */
@PreAuthorize("hasRole('USER')")
@Operation(summary = "Добавление объявления")
@ResponseStatus(HttpStatus.CREATED)
@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public AdDto addAd(
        @RequestPart("properties") CreateOrUpdateAd properties,
        @RequestPart("image") MultipartFile image) throws Exception {

    return adService.addAd(properties, image);
}

/**
 * Получает полную информацию об объявлении по его ID
 * @param id идентификатор объявления
 * @return расширенное DTO объявления с контактными данными автора
 */
@PreAuthorize(USER)
@Operation(summary = "Получение информации об объявлении")
@GetMapping("/{id}")
public ExtendedAd getAd(@PathVariable Integer id){
    return adService.getAd(id);
}

/**
 * Удаляет объявление по его ID
 * @param id идентификатор удаляемого объявления
 */
@PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfAd(#id)")
@Operation(summary = "Удаление объявления")
@DeleteMapping("/{id}")
public void deleteAd(@PathVariable Integer id){
    adService.deleteAd(id);
}

/**
 * Обновляет информацию об объявлении
 * @param id идентификатор обновляемого объявления
 * @param dto новые данные объявления
 * @return обновленное DTO объявления
 */
@PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfAd(#id)")
@Operation(summary = "Обновление информации об объявлении")
@PatchMapping("/{id}")
public AdDto updateAd(@PathVariable Integer id,@RequestBody @Valid CreateOrUpdateAd dto){
    return adService.updateAd(id,dto);
}

/**
 * Получает список объявлений текущего авторизованного пользователя
 * @return объект Ads, содержащий количество и список DTO объявлений пользователя
 */
@PreAuthorize("hasRole('USER')")
@Operation(summary = "Получение объявлений авторизованного пользователя")
@GetMapping("/me")
public Ads getMyAds(){
    return adService.getMyAds();
}


}
