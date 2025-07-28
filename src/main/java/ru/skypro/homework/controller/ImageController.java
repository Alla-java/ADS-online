package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Изображения")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping(value = "/users/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam MultipartFile image) throws IOException {
        imageService.uploadUserImage(image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление изображения объявления")
    @PatchMapping(value = "/ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAdImage(@PathVariable Integer id,
                                              @RequestParam MultipartFile image) throws IOException {
        imageService.uploadAdImage(id, image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение изображения объявления")
    @GetMapping(value = "/ads/{id}/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getAdImage(@PathVariable Integer id) {
        return ResponseEntity.ok(imageService.getImage(id));
    }

    @Operation(summary = "Получение изображения объявления")
    @GetMapping(value = "/images/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        System.out.println("картинка");
        return ResponseEntity.ok(imageService.getImage(id));

    }
}
