package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

/**
 * Контроллер для работы с изображениями.
 * Обеспечивает загрузку и получение изображений пользователей и объявлений.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Изображения")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {
    private final ImageService imageService;

    /**
     * Обновляет аватар текущего авторизованного пользователя
     * @param image файл нового аватара
     * @return ResponseEntity с кодом 200 при успешном обновлении
     * @throws IOException при ошибке обработки файла
     */
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping(value = "/users/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam MultipartFile image) throws IOException {
        imageService.uploadUserImage(image);
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет изображение объявления
     * @param id идентификатор объявления
     * @param image файл нового изображения
     * @return ResponseEntity с массивом байтов изображения и кодом 201
     * @throws IOException при ошибке обработки файла
     */
    @Operation(summary = "Обновление изображения объявления")
    @PatchMapping(value = "/ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateAdImage(@PathVariable Integer id,
                                              @RequestParam MultipartFile image) throws IOException {
        imageService.uploadAdImage(id, image);
        byte[] imageData = imageService.getImage(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(imageData);
    }

    /**
     * Получает изображение объявления по его ID
     * @param id идентификатор объявления
     * @return ResponseEntity с массивом байтов изображения
     */
    @Operation(summary = "Получение изображения объявления")
    @GetMapping(value = "/ads/{id}/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getAdImage(@PathVariable Integer id) {
        return ResponseEntity.ok(imageService.getImage(id));
    }

    /**
     * Получает изображение по его ID
     * @param id идентификатор изображения
     * @return ResponseEntity с массивом байтов изображения
     */
    @Operation(summary = "Получение изображения объявления")
    @GetMapping(value = "/images/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        System.out.println("картинка");
        return ResponseEntity.ok(imageService.getImage(id));

    }
}
